library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.NUMERIC_STD.ALL;
entity testbench is
--empty
end testbench;

architecture tb of testbench is
--DUT component
component Booth_Mult is --connects entity to testbench
	Port (  In_1, In_2: in std_logic_vector(7 downto 0);
    		clk: in std_logic;
   			ready: in std_logic;
    		done: out std_logic;
    		S: out std_logic_vector(15 downto 0));
end component;
--Testing signals
signal In1, In2: std_logic_vector(7 downto 0);
signal clk_in : std_logic := '0';
signal ready_in : std_logic := '0';
signal done_out: std_logic;
signal S_out: std_logic_vector(15 downto 0);
begin
	--Connect DUT
    -- Map the testing vectors to their corresponding
    DUT: Booth_Mult port map (In1, In2,clk_in, ready_in, done_out, S_out);
    clk_process: process
    begin
    	for i in 0 to 7 loop
          clk_in <= '0';
          wait for 0.5 ns;  --for 0.5 ns signal is '0'.
          clk_in <= '1';
          wait for 0.5 ns;  --for next 0.5 ns signal is '1'.
        end loop;
        wait;
    end process;
    process
    	begin
        ready_in <= '0';
        wait for 1 ns;
        
        ready_in <= '1';
        In1 <= "00000111";
        In2 <= "11111111";
        -- 7*-1 = -7 (1111111111111001)
        wait for 1 ns;
        
        
        
        ready_in <= '0';
        wait for 1 ns;
        
        ready_in <= '1';
        In1 <= "00000111";
        In2 <= "00000001";
        -- 7*1 = 7 (0000000000000111)
        wait for 1 ns;
        
        ready_in <= '0';
        wait for 1 ns;
        
        ready_in <= '1';
        In1 <= "10011000";
        In2 <= "10001100";
        -- -104 * -116 = 12064 (0010111100100000)
        wait for 1 ns;
        
        ready_in <= '0';
        wait for 1 ns;
        
        ready_in <= '1';
        In1 <= "01110001";
        In2 <= "10101010";
        -- 113 * -86 = -9718 (1101101000001010)
        wait for 1 ns;
        
        ready_in <= '0';
        wait for 1 ns;
        
        ready_in <= '1';
        In1 <= "10000001";
        In2 <= "10000010";
        -- -127 * -126 = 16002 (0011111010000010)
        wait for 1 ns;
        
        ready_in <= '0';
        wait for 1 ns;
        
        ready_in <= '1';
        In1 <= "10011000";
        In2 <= "01100010";
        -- -104 * 98 = -10192 (1101100000110000)
        wait for 1 ns;
        
        ready_in <= '0';
        wait for 1 ns;
    
    	-- clear inputs
        In1 <= "00000000";
        In2 <= "00000000";
		wait;
        
        --print something about run time = 
    end process;
 end tb;
