-- Code your testbench here
library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;

entity testbench is
--empty
end testbench;

architecture tb of testbench is

--DUT component
component FP_Adder is --connects entity to testbench
	port (A, B : in std_logic_vector(31 downto 0);
    		rule : in std_logic;
    	  S : out std_logic_vector(31 downto 0));
end component;
-- Testing signals
signal A_in, B_in, S_out: std_logic_vector(31 downto 0);
signal rule: std_logic;
begin
	--Connect DUT
    -- Map the testing vectors to their corresponding
    -- input and output vectors.
    DUT: FP_Adder port map (A_in, B_in, rule, S_out);
    process
    begin
    	rule <= '0';
   --Case where A=0 so outputs B
         A_in <= "00000000000000000000000000000000";
        B_in <= "00001100000000000000000000000000";
        wait for 1 ns;
  --Case where A=Nan and B=finite so outputs Nan(A)
        A_in <= "01111111100000000000000001000110";
        B_in <= "01000001110011001010001111010111";
        wait for 1 ns;
   --Case where A=finite and B=Nan so outputs Nan(B)
      
        A_in <= "01000001110011001010001111010111";
        B_in <= "01111111100000000000000001000110";
        wait for 1 ns;
    --Case where A=inf and B=finite so outputs inf
        A_in <= "01111111100000000000000000000000";
        B_in <= "01000001110011001010001111010111";
        wait for 1 ns;
     --Case where A=finite and B=-inf  so outputs -inf
        A_in <= "01111100100000000000000000000000";
        B_in <= "11111111100000000000000000000000";
        wait for 1 ns;
    --case where A=inf and B=-inf so outputs NAN 
        A_in <= "01111111100000000000000000000000";
        B_in <= "11111111100000000000000000000000";
        wait for 1 ns;
    
    --    100.349998474+25.5799999237=125.929998398
        A_in <= "01000010110010001011001100110011";
        B_in <= "01000001110011001010001111010111";
        rule <= '0';
        -- S -> 01000010111110111101110000101000
        wait for 1 ns;
        
   --   (-100.349998474)+25.5799999237= -74.7699985503 
        A_in <= "11000010110010001011001100110011";
        B_in <= "01000001110011001010001111010111";
        rule <= '0';
        wait for 1 ns;
        
   --  6.59309437241e+19+(-6.91752902764e+19)=-3.24434655226e+18
        A_in <= "01100000011001001011111001110001";
        B_in <= "11100000011100000000000000000000";
        rule <= '0';
        wait for 1 ns;
        
        
        --Clear inputs
        A_in <= "00000000000000000000000000000000";
        B_in <= "00000000000000000000000000000000";
        rule <= '0';
        wait;
        
	end process;
end tb;
