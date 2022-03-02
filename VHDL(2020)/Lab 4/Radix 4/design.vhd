
-- Code your design here
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.NUMERIC_STD.ALL;
entity Booth_Mult is 
	Port (  In_1, In_2: in std_logic_vector(7 downto 0);
    		clk: in std_logic;
    		ready: in std_logic;
    		done: out std_logic;
    		S: out std_logic_vector(15 downto 0));
	
end Booth_Mult;


architecture RTL of Booth_Mult is
begin
	
	Booth_Mult:process(In_1, In_2, clk, ready)
    --variable declarations
    variable M: std_logic_vector(7 downto 0);
    variable Q: std_logic_vector(8 downto 0);
    variable A: std_logic_vector(8 downto 0);--partial accumulator
    -- A has an extra bit to handle overflow
    -- variable to hold A & Q for S
    variable shifter: std_logic_vector(17 downto 0);
    
	begin
    	-- Active high
    	if rising_edge(clk) then
          -- ready to begin
          if ready = '1' then
            -- initialize variables
            A := "000000000";
            M := In_1;
      		Q := In_2 & '0';
            shifter := A & Q;
            done <= '0';
    		  -- perform the procedure n/2 (4) times
              for i in 0 to 3 loop
                if Q(2 downto 0) = "000" then --2 cases where A=A
                   A := A; 
                  
                   
                   
                elsif Q(2 downto 0)="001" then--2 cases when A=A+M
                   A := std_logic_vector(signed(A) + signed(M));
               
                    
                elsif Q(2 downto 0) ="010" then
                    A := std_logic_vector(signed(A) + signed(M)); 
               
                    
                    
                elsif Q(2 downto 0) = "011" then--cases where A=A+2M
                	  A := std_logic_vector(signed(A) + signed(M)); 
                      A := std_logic_vector(signed(A) + signed(M));
            
      			 
                    
                    
                    
                elsif Q(2 downto 0) = "100" then--2 cases where A=A-2M
                	A := std_logic_vector(signed(A) - signed(M)); 
                    A := std_logic_vector(signed(A) - signed(M));
               
                    
                    
                    
                elsif Q(2 downto 0) = "101" then
                  A := std_logic_vector(signed(A) - signed(M));
         
                    
                    
                elsif Q(2 downto 0) = "110" then
                  A := std_logic_vector(signed(A) - signed(M));
               
                    
                    
                    
                elsif Q(2 downto 0) = "111" then
                	A:=A;
               
                end if;
                   -- A is concatenated with Q and stored in 'shifter'
                   shifter := A & Q;
                   -- shifter is arithetically shifted right two times
                   -- First shift
      			   shifter := std_logic_vector(shift_right(signed(shifter), 1));
              		-- Ensure that the sign is retained
                     shifter:= shifter(16 downto 16) & shifter(16 downto 0);
                    -- Second shift
                    shifter := std_logic_vector(shift_right(signed(shifter), 1));
                    -- Ensure that the sign is retained
       				shifter:= shifter(16 downto 16) & shifter(16 downto 0);
                    -- Reassign A and Q for the next iteration
                    A := shifter(17 downto 9);
                    Q := shifter(8 downto 0);
              end loop;
              -- Procedure is finished. Set done to 1 and assign S to A & Q truncating the hiddin bit and the bit that was added to handle overflow (MSB)
              done <= '1';
              S <= shifter(16 downto 1);
          end if;
        end if;
	end process;
end RTL;