package de.htwg.memory;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import de.htwg.memory.activity.GameActivity;

/**
 * this class represent a game card, which can be clicked and turned each card has a common backimage
 * two cards with the same cardid have the same unique frontimage
 * 
 * @author Simon Schneeberger <sischnee@gmail.com>
 * @version V1.0 18-01-2014
 */
public class CheckCards extends AsyncTask<Void, Void, int[]> {
	
	private static MemoryCard[] cards;
	private final int row;
	private final int col;
	private static GameActivity gameActivity;
	private static int[] checkCardArray = new int[2];
	
	/**
	 * @param gameActivity is the GameActivity Class which calls this class
	 * @param cards is the array of cards
	 * @param connection 
	 * @param row is the number ow rows in the game
	 * @param col is the number ow rows in the game
	 */
	@SuppressLint("DefaultLocale")
	public CheckCards(final GameActivity gameActivity, final MemoryCard[] cards, int row, int col) {	
		super();
		CheckCards.gameActivity = gameActivity;
		MemoryCard[] cardArray = cards;
		CheckCards.cards = cardArray;
		this.row = row;
		this.col = col;					
	}
	
	/**
	 * to check which cards a clicked, only two cards can be clicked
	 * more than two clicked cards will be ignored
	 * 
	 * future information @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected int[] doInBackground(Void... params) {
		
		//create an cardArray which have the two clicked cards
		//identifier is the index in the cards array
		int[] cardArray = new int[2];
		cardArray[0] = -1;
		cardArray[1] = -1;
		
		int anzClickedCards = 0;
		
		for(int i=0; i<row*col; i++) {
			
			if(cards[i].isClicked()) {
				cardArray[anzClickedCards++] = i;
				
				if(anzClickedCards >= 2) {
					break;
				}
			}
		}
			
		for(int i=0; i<row*col; i++) {
			
			if(anzClickedCards < 2 && cards[i].getWantToTurn()) {
				cards[i].setClicked(true);
				cards[i].setWantToTurn(false);
				cardArray[anzClickedCards++] = i;
				if(anzClickedCards >= 2) {
					break;
				}
			}
			else {
				cards[i].setWantToTurn(false);
			}
		}
		return cardArray;
	}	
	
	/**
	 * onPostExecute is called after doInBackground
	 * method is necessary to flip cards in both ways(show frontimage and backimage)
	 * 
	 * furture information @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(int[] cardArray) {
		
		checkCardArray = cardArray;
		
		//turn the clicked cards to front
		for(int i=0; i<2; i++) {
			if(cardArray[i] >= 0) {
				cards[cardArray[i]].turnFront();
			}
		}
		
		//only if two cards are clicked check whether they are couples 
		if(cardArray[0] != -1 && cardArray[1] != -1) {
			
			if(cards[cardArray[0]].getTurnBack() == 0 && cards[cardArray[1]].getTurnBack() == 0) {
				cards[cardArray[0]].setTurnBack(1);
				cards[cardArray[1]].setTurnBack(1);
				
				// read status from server
				try {
					MemoryConnection connection = new MemoryConnection();
					connection.readCardState(cards[cardArray[0]].getCardId(), cards[cardArray[1]].getCardId());
				} catch (Exception e){
					Log.d("error", e.toString());
				}
				
			}
			if(cards[cardArray[0]].getTurnBack() == 2 || cards[cardArray[1]].getTurnBack() == 2) {
				cards[checkCardArray[0]].turnBack();
				cards[checkCardArray[0]].setTurnBack(0);
				cards[checkCardArray[0]].setClicked(false);
				
				cards[checkCardArray[1]].turnBack();
				cards[checkCardArray[1]].setTurnBack(0);
				cards[checkCardArray[1]].setClicked(false);
			}
		}
	}
	
	@SuppressWarnings("static-access")
	public static void handle(int ret){		
		Log.d("handle",String.valueOf(ret));
		
		//error message if connection is interrupted
		if(ret == -1) {
        	AlertDialog.Builder builder = new AlertDialog.Builder(gameActivity);
    		builder.setTitle("ERROR")
    		       .setCancelable(false)
    		       .setMessage("cannot read card state - no connection to server")
    		       .setPositiveButton("Close", new DialogInterface.OnClickListener() {
    		           public void onClick(DialogInterface dialog, int id) {
    		        	   gameActivity.finish();
    		           }
    		       });
    		AlertDialog alert = builder.create();
    		alert.show();
		}		

		if(ret == 1) {			
			gameActivity.locationListener.checkCoordinates(cards[checkCardArray[0]], cards[checkCardArray[1]]);
			cards[checkCardArray[0]].setTurnBack(0);
			cards[checkCardArray[1]].setTurnBack(0);
		}
		else if(ret == 2) {
			gameActivity.locationListener.lastCheckCoordinates(cards[checkCardArray[0]], cards[checkCardArray[1]]);
		}
		else {
    	    Integer anz = GameActivity.getTries()+1;
    	    GameActivity.setTries(anz);
			cards[checkCardArray[0]].setTurnBack(2);
			cards[checkCardArray[1]].setTurnBack(2);
		}
	}
}
