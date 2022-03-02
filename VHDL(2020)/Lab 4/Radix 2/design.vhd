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
    variable A: std_logic_vector(7 downto 0);--partial accumulator
    -- variable to hold A & Q for S
    variable shifter: std_logic_vector(16 downto 0);
    
	begin
    	-- Active high
    	if rising_edge(clk) then
          -- ready to begin
          if ready = '1' then
          	-- initialize variables
            A := "00000000";
            M := In_1;
            Q := In_2 & '0';
            shifter := A & Q;
            done <= '0';
			  -- perform the procedure n (8) times
              for i in 0 to 7 loop
                  if Q(1 downto 0) = "01" then
                  	  -- Add M to A
                      A := std_logic_vector(signed(A) + signed(M)); 
  					  -- concatenate A with Q
                      shifter := A & Q;
                      -- Arithmetic shift right by 1 bit
                      shifter := std_logic_vector(shift_right(signed(shifter), 1));
                      A := shifter(16 downto 9);
                      Q := shifter(8 downto 0);
                  elsif Q(1 downto 0) = "10" then
                  	  -- subtract M from A
                      A := std_logic_vector(signed(A) - signed(M)); 
                      -- concatenate A with Q
                      shifter := A & Q;
                      -- Arithmetic shift right by 1 bit
                      shifter := std_logic_vector(shift_right(signed(shifter), 1));
                      A := shifter(16 downto 9);
                      Q := shifter(8 downto 0);

                  else 
                  -- Q(1 downto 0) is either "00" or "11"
                  	  -- concatenate A with Q (A unchanged)
                      shifter := A & Q;
                      -- Arithmetic shift right by 1 bit
                      shifter := std_logic_vector(shift_right(signed(shifter), 1));
                      A := shifter(16 downto 9);
                      Q := shifter(8 downto 0);
                  end if;
              end loop;
              -- Procedure is finished. Set done to 1 and assign S to A & Q truncating the hiddin bit
              done <= '1';
              S <= shifter(16 downto 1);
          end if;
        end if;
	end process;
end RTL;
