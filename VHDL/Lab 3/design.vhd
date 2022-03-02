-- Code your design here
library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;

entity FP_Adder is
	port (A, B : in std_logic_vector(31 downto 0);
    		rule : in std_logic;
    	  S : out std_logic_vector(31 downto 0));
end FP_Adder;

architecture RTL of FP_Adder is

begin
FP_Adder:process(A,B)
	--breakdown of A and B
    variable H_A : std_logic; -- Hidden bit of A
	variable H_B : std_logic; -- Hidden bit of B
   	variable S_A : std_logic; -- sign of A
	variable S_B : std_logic; -- sign of B
    variable E_A : std_logic_vector(7 downto 0); -- Exponent of A
	variable E_B : std_logic_vector(7 downto 0); -- Exponent of B
	variable M_A : std_logic_vector(22 downto 0); -- Mantissa of A
	variable M_B : std_logic_vector(22 downto 0); -- Mantissa of B
    variable  C_A : std_logic_vector(2 downto 0);--cases where 000 is a real number
	variable  C_B : std_logic_vector(2 downto 0);
    variable diff : integer range 0 to 10000; -- difference between the exponent values
    
    
    
    -- temporary variable(s) for swap of contents of A and B
    
    variable S_temp : std_logic;
    variable E_temp : std_logic_vector(7 downto 0);
    variable M_temp : std_logic_vector(22 downto 0);
    variable H_temp : std_logic;
    variable shifted_M_A : std_logic_vector(48 downto 0);
    variable shifted_M_B : std_logic_vector(48 downto 0);
    variable M_A_H_A : std_logic_vector(23 downto 0);
    variable M_B_H_B : std_logic_vector(23 downto 0);
    variable result_M : std_logic_vector(48 downto 0);
    variable carry_out_M : std_logic_vector(49 downto 0);
    variable S_S: std_logic;--sign of S
    variable S_E: std_logic_vector(7 downto 0);--Exponent of S
    variable S_M: std_logic_vector(22 downto 0);--Mantissa of S
    variable count : integer range 0 to 50;
   	begin
    	S_A := A(31);
        S_B := B(31);
        E_A := A(30 downto 23);
        E_B := B(30 downto 23);
        M_A := A(22 downto 0);
        M_B := B(22 downto 0);
    	H_A := '1';
        H_B := '1';
        diff := to_integer(unsigned(E_A) - unsigned(E_B));
        shifted_M_A := M_A & "00000000000000000000000000";
        shifted_M_B := M_B & "00000000000000000000000000";
        C_A := "000";
        C_B := "000";
        count := 0;
        
		-- Check if any of the exponents are 0.
		-- If so their corresponding hidden bit is 0.
		if E_A = "00000000" then
			H_A := '0';
		end if;
       
		if E_B = "00000000" then
			H_B := '0';
		end if;
        
        
        
        
        
        
        
        
        
        
        --cases
        
		--set up for all the special cases of A
		if (E_A="00000000") and (M_A="00000000000000000000000") then

			if S_A='0' then
    			C_A:="001";--case 1 (N=0)
    		elsif S_A='1' then
    			C_A:="010";--case 2 (N=-0)
    		end if;
    
		elsif E_A="11111111" then
			if(M_A="00000000000000000000000") then
				if S_A='0' then
    				C_A:="011";--case 3 (N=infinity)
        
    			elsif S_A='1' then
    				C_A:="100";--case 4 (N=-infinity)
        
    			end if;
     		else
     		C_A:="101";--case 5 (N=nan)
     		end if;
     	
		else
			C_A:="000";--case 0 (N=(-1)^S * 1.M * 2^(E-127))
		end if;

		--set up for all the special cases of B
		if (E_B="00000000") and (M_B="00000000000000000000000") then

			if S_B='0' then
    			C_B:="001";--case 1 (N=0)
        
   			elsif S_B='1' then
    			C_B:="010";--case 2 (N=-0)
        
    		end if;
    
		elsif E_B="11111111" then
			if(M_B="00000000000000000000000") then
				if S_B='0' then
    				C_B:="011";--case 3 (N=infinity)
        
    			elsif S_B='1' then
    				C_B:="100";--case 4 (N=-infinity)
        
    			end if;
     		else
     			C_B:="101";--case 5 (N=nan)
     		end if;
     	
		else
			C_B:="000";--case 0 (N=(-1)^S * 1.M * 2^(E-127))
		end if;













--assign vales


		-- Cases where A or B are +-0
       if C_A="001" then
        	S<=B;
        elsif C_A="010" then
        	S<=B;
        elsif C_B="001" then
        	S<=A;
        elsif C_B="010" then
        	S<=A;
            
      --cases where A or B are NAN
        elsif C_A="101" then
        	S<=A;
        elsif C_B="101" then
        	S<=B;
       --cases where A or B are infinity 
		elsif C_A="011" then --A=inf
			if C_B="100" then-- (infinity+-infinity)=NAN
   				--NAN
                S<="01111111100000000000000000000001";
   			else
   				S<="01111111100000000000000000000000";--infinity
  			end if;
		elsif C_B="011" then--B=inf
    		if C_A="100" then-- (-inf+inf)=NAN
        		--NAN
                S<="01111111100000000000000000000001";
       		else
        		S<="01111111100000000000000000000000";--infinity
       		end if;
  
  --case
		elsif (C_A="100") or  (C_B="100")then--if A or B are negative infinity, since we already checked for either A or B infinity then output is negative infinity, as we have either negative inf + negative inf or negative inf + real 
        	S<="11111111100000000000000000000000";
            
           --both are not special cases
        else 
        	if(E_B > E_A) then
                -- swap the contents of B and A
                S_temp := S_A;
                E_temp := E_A;
                M_temp := M_A;
                H_temp := H_A;
                S_A := S_B;
                E_A := E_B;
                M_A := M_B;
                H_A := H_B;
                S_B := S_temp;
                E_B := E_temp;
                M_B := M_temp;
                H_B := H_temp;
                diff := to_integer(unsigned(E_A) - unsigned(E_B));
            end if;
            if(E_B = E_A) then
            	if(M_B > M_A) then
                  -- swap the contents of B and A
                  S_temp := S_A;
                  E_temp := E_A;
                  M_temp := M_A;
                  H_temp := H_A;
                  S_A := S_B;
                  E_A := E_B;
                  M_A := M_B;
                  H_A := H_B;
                  S_B := S_temp;
                  E_B := E_temp;
                  M_B := M_temp;
                  H_B := H_temp;
                  diff := to_integer(unsigned(E_A) - unsigned(E_B));
               end if;
            end if;
            -- initialize
            -- initialize large mantissa variables
            shifted_M_A := H_A & M_A & "0000000000000000000000000";
        	shifted_M_B := H_B & M_B & "0000000000000000000000000";
            -- if diff is greater than zero we have to perform a shift
            if(diff > 0) then
            	-- shift B to the right diff times
                -- FILLED IN WITH 0s or 1s??
            	shifted_M_B := std_logic_vector(shift_right(unsigned(shifted_M_B), diff));
                -- add diff to the exponent of B
            	E_B := std_logic_vector(unsigned(E_B) + to_unsigned(diff, 8));
            end if;
            -- if A and B have the same sign
            if((S_A = '1' AND S_B = '1') OR (S_A = '0' AND S_B = '0')) then
            	-- Add the mantissas of A and B to result (size 44)
            	result_M := std_logic_vector(unsigned(shifted_M_A) + unsigned(shifted_M_B));
                -- Add the mantissas of A and B to a 45 bit vector
                carry_out_M := std_logic_vector(unsigned('0' & shifted_M_A) + unsigned(shifted_M_B));
                -- Check if we have a carry out
                -- I DONT THINK THIS IS CORRECT NEEDS TO BE FIXED
            	 if ('0' & result_M /= carry_out_M(49 downto 0)) then
                 -- We have a carry out so add one to the exponent
                 E_A := std_logic_vector(unsigned(E_A) + '1');
                 -- Shift right
                 result_M := std_logic_vector(shift_right(unsigned(result_M), 1));
                 -- Set the hidden bit to be 1
                 result_M(48) := '1';
                 end if;
                 if(E_A="11111111") then
                 	S<=(S_A & E_A & "00000000000000000000000");
                 else
                 	if(rule = '0') then
                 	S <= (S_A & E_A & result_M(47 downto 25));
                    else
                    	if(result_M(24) = '0') then
                        	S <= (S_A & E_A & result_M(43 downto 21));
                        else
                        	result_M(47 downto 25) := std_logic_vector(unsigned(result_M(47 downto 25)) + "00000000000000000000001");
                            S <= (S_A & E_A & result_M(47 downto 25));
                        end if;
                    end if;
                 end if;
            else
            	-- A and B have different signs
                -- subtract B from A
            	result_M := std_logic_vector(unsigned(shifted_M_A) - unsigned(shifted_M_B));
                carry_out_M := std_logic_vector(unsigned('0' & shifted_M_A) - unsigned(shifted_M_B));
                -- Check if we have a carry out
                -- I DONT THINK THIS IS CORRECT NEEDS TO BE FIXED
                  -- Shift left until we reach 1 as the MSB
                while(result_M(48) = '0') loop
				  result_M := std_logic_vector(shift_left(unsigned(result_M), 1));
                  -- Keep count of how much we have shifted
                  count := count + 1;
                  exit when count = 30;
                end loop;
                  -- Subtract from the exponent value
                E_A := std_logic_vector(unsigned(E_A) - to_unsigned(count, 8));
                if(result_M = "0000000000000000000000000000000000000000000000000") then
                	S<=(S_A & "0000000000000000000000000000000");
                elsif(E_A="11111111") then
                	S<=(S_A & E_A & "00000000000000000000000");
                else
                	if (rule = '0') then
                    S <= (S_A & E_A & result_M(47 downto 25));
                    else
                    	if(result_M(24) = '0') then
                        	S <= (S_A & E_A & result_M(47 downto 25));
                        else
                        	result_M(47 downto 25) := std_logic_vector(unsigned(result_M(47 downto 25)) + "00000000000000000000001");
                            S <= (S_A & E_A & result_M(47 downto 25));
                        end if;
                    end if;
                end if;
            end if;
        end if;
    end process;

end RTL;