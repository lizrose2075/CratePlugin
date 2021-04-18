package com.lizminecraft.crateplugin;

public class PrizeLogic {
	
	private static int firstPrize = 3; //chance = 3%
	private static int secondPrize = 8; //chance = 5%
	private static int thirdPrize = 16; //chance = 8%
	private static int fourthPrize = 32; //chance = 16%
	
	public static int prizeWon() {
		//0 = first etc
		int randomNumber = (int) Math.random() * 100;
		if(randomNumber <= firstPrize) {
			return 0;
		}else if(randomNumber <= secondPrize) {
			return 1;
		}else if(randomNumber <= thirdPrize) {
			return 2;
		}else if(randomNumber <= fourthPrize) {
			return 3;
		}else {
			//wins 5th prize
			return 4;
		}
		
	}
	
	
}
