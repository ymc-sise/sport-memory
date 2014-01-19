package de.htwg.memory;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;
import de.htwg.memory.activity.GameActivity;

/**
 * this represent the connection class, which is necessary for the communication between android app and webserver
 * IMPORTANT: more than one communication instance will destroy the session in the webserver 
 *  
 * @author Simon Schneeberger <sischnee@gmail.com>
 * @version V1.0 18-01-2014
 * 
 */
public final class MemoryConnection extends AsyncTask<JSONObject, Integer, JSONArray> {
	
	private int switchMethod = 0;
	private DefaultHttpClient httpClient;
	private HttpPost httppost;
	
	/**
	 * contructor
	 */
	public MemoryConnection() {
		httpClient = BaseConnection.getInstance().httpClient;	
        httppost = BaseConnection.getInstance().httppost;
	}
		
	
  	/* (non-Javadoc)
	 * @see de.htwg.memory.IConnection#readMemory(int, int)
	 */
    public void readMemory(int row, int col) throws JSONException, IOException {
 	    	
    	switchMethod = 0;
    	
    	JSONObject parentData = new JSONObject();
    	parentData.put("action", "create");
    	
    	JSONObject childData = new JSONObject();
    	childData.put("row", Integer.valueOf(row));
    	childData.put("column", Integer.valueOf(col));
    	
       	parentData.put("params", childData);
       	       	
       	//call request function and return the result
       	this.execute(parentData);
       		
       	//validMemory = true;
       	//return object.getJSONArray("result");
	}
   
    /* (non-Javadoc)
	 * @see de.htwg.memory.IConnection#readCardState(int, int)
	 */
	public void readCardState(int firstCard, int secondCard) throws JSONException, IOException {
				
		switchMethod = 1;
		
		JSONObject parentData = new JSONObject();
    	parentData.put("action", "move");
    	
    	JSONObject childData = new JSONObject();
    	childData.put("firstCard", Integer.valueOf(firstCard));
    	childData.put("secondCard", Integer.valueOf(secondCard));
    	    	
       	parentData.put("params", childData);

       	//call request function and return the result
       	this.execute(parentData);
       	
       	//return object.getInt("result");		
	}

	/**
	 * @param parentData  contains parameter for the webserver for exmaple: 
	 * { "action": "move", "params": {"firstCard": 4, "secondCard":5 } }
	 * { "action": "create", "params": {"row": 4, "column":5 } }
	 * @return a jsonAraay which have the response answer from webserver
  	 * @throws JSONException from JSON
	 * @throws JSONException  execute()
	 */
	@Override
	protected JSONArray doInBackground(JSONObject... parentData) {
		// TODO Auto-generated method stub
        try {        	
  
            StringEntity se = new StringEntity( parentData[0].toString());  
            se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            httppost.setEntity(se);
            
    		HttpResponse response = httpClient.execute(httppost);
    		
    		JSONObject result = null;
    		
    		StatusLine status = response.getStatusLine();
            Integer statusCode = status.getStatusCode();
            Log.d("StatusCode", statusCode.toString());
                    
            if (statusCode == 200) {
            	HttpEntity entity = response.getEntity();
            	String ret = EntityUtils.toString(entity);
            	result = new JSONObject(ret);
                String re = result.getString("result");

                if(re.equals("error")) {
                	Log.d("result", statusCode.toString());
                	return null;
                }
                else {
                	return result.getJSONArray("result");
                }
            }
    		
            return null;    		
    		
		} catch (Exception e) {
			Log.e("Error", e.toString());
		}

        return null;
	}
	
	@Override
	protected void onPostExecute(JSONArray result) {
		if ( switchMethod == 0 ){
	        GameActivity.drawGame(result);
		}else{
			try {
				CheckCards.handle(result.getInt(0));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Log.e("error",e.toString());
			}			
		}
	}
	
 }
	
	
    
