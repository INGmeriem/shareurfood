package com.example.elazaoui.projet;

/**
 * Created by elazaoui on 02/03/16.
 */



import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ServerRequests {
    ProgressDialog progressDialog;
    public static final int CONNECTION_TIMEOUT = 1000 * 15;
    public static final String SERVER_ADDRESS = "http://shareurfood.esy.es/";
    private Session session;//global variable

    public ServerRequests(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing...");
        progressDialog.setMessage("Please wait...");
        session = new Session(context); //in oncreate
    }

    public void storeUserDataInBackground(User user, GetUserCallback userCallBack) {
        progressDialog.show();
        new StoreUserDataAsyncTask(user, userCallBack).execute();
    }
    public void storeFoodDataInBackground(Food food, GetFoodCallback foodCallBack) {
        progressDialog.show();
        new StoreFoodDataAsyncTask(food, foodCallBack).execute();
    }

    public void fetchUserDataAsyncTask(User user, GetUserCallback userCallBack) {
        progressDialog.show();
        new fetchUserDataAsyncTask(user, userCallBack).execute();
    }

    public void fetchFoodDataAsyncTask(Food food, GetFoodCallback foodCallback) {
        progressDialog.show();
        new fetchFoodDataAsyncTask(food, foodCallback).execute();
    }

    /**
     * parameter sent to task upon execution progress published during
     * background computation result of the background computation
     */

    public class StoreUserDataAsyncTask extends AsyncTask<Void, Void, Void> {
        User user;
        GetUserCallback userCallBack;

        public StoreUserDataAsyncTask(User user, GetUserCallback userCallBack) {
            this.user = user;
            this.userCallBack = userCallBack;
        }

        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("name", user.name));
            dataToSend.add(new BasicNameValuePair("username", user.username));
            dataToSend.add(new BasicNameValuePair("password", user.password));
            dataToSend.add(new BasicNameValuePair("age", user.age + ""));
            dataToSend.add(new BasicNameValuePair("adresse", user.adresse));
            dataToSend.add(new BasicNameValuePair("arrondissement", user.arrondissement));
            HttpParams httpRequestParams = getHttpRequestParams();

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS
                    + "Register.php");

            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                client.execute(post);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        private HttpParams getHttpRequestParams() {
            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams,
                    CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams,
                    CONNECTION_TIMEOUT);
            return httpRequestParams;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            userCallBack.done(null);
        }

    }


    public class StoreFoodDataAsyncTask extends AsyncTask<Void, Void,Void> {
        Food food;
        GetFoodCallback foodCallBack;

        public StoreFoodDataAsyncTask(Food food, GetFoodCallback foodCallBack) {
            this.food = food;
            this.foodCallBack = foodCallBack;
        }
        /*CreateFood.php:
ajouter food dans la table food
         AddFoodPanier.php :
ajouter food au panier    */
        @Override
        protected Void doInBackground(Void... params) {
            try {
                //sil existe un string avec des espaces on rajoute la fonction replace :)
                //String username = "coco";
                String username = session.getusername();
                String nomP = food.nomP;
                nomP=nomP.replace(" ", "%20");
                String descriptionP = food.descriptionP;
                descriptionP=descriptionP.replace(" ", "%20");
                double prixP = food.prixP;
                String imageP = food.imgP;
                imageP=imageP.replace(" ", "%20");
                int quantiteP = food.quantiteP;
                int typeP = food.typeP;

                String link = "http://shareurfood.esy.es/CreateFood.php?username=%22"+username+"%22&nomP=%22"+nomP+"%22&descriptionP=%22"+descriptionP+"%22&prixP="+prixP+"&imageP=%22"+imageP+"%22&quantiteP="+quantiteP+"&typeP="+typeP+"";
                System.out.print(link);
                    URL url = new URL(link);
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));
                HttpResponse response = client.execute(request);
                BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                in.close();
                System.out.println(sb.toString());
            } catch (Exception e) {
                return null;
            }
            return null;
        }

        private HttpParams getHttpRequestParams() {
            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams,
                    CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams,
                    CONNECTION_TIMEOUT);
            return httpRequestParams;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            foodCallBack.done(null);
        }

    }

    public class fetchUserDataAsyncTask extends AsyncTask<Void, Void, User> {
        User user;
        GetUserCallback userCallBack;

        public fetchUserDataAsyncTask(User user, GetUserCallback userCallBack) {
            this.user = user;
            this.userCallBack = userCallBack;
        }

        @Override
        protected User doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("username", user.username));
            dataToSend.add(new BasicNameValuePair("password", user.password));

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams,
                    CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams,
                    CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS
                    + "FetchUserData.php");

            User returnedUser = null;

            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse httpResponse = client.execute(post);

                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);
                JSONObject jObject = new JSONObject(result);

                if (jObject.length() != 0){
                    Log.v("share food", "2");
                    String name = jObject.getString("name");
                    int age = jObject.getInt("age");
                    String adresse = jObject.getString("adresse");
                    String arrondissement = jObject.getString("arrondissement");
                    returnedUser = new User(name, age, user.username,user.password,adresse, arrondissement);
                    session.setusername(user.username + "");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return returnedUser;
        }

        @Override
        protected void onPostExecute(User returnedUser) {
            super.onPostExecute(returnedUser);
            progressDialog.dismiss();
            userCallBack.done(returnedUser);
        }
    }

    public class fetchFoodDataAsyncTask extends AsyncTask<Void, Void, Food> {
        Food food;
        GetFoodCallback foodCallBack;

        public fetchFoodDataAsyncTask(Food food, GetFoodCallback foodCallBack) {
            this.food = food;
            this.foodCallBack = foodCallBack;
        }

        @Override
        protected Food doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("typeP", food.typeP + ""));

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams,
                    CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams,
                    CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS
                    + "FetchFoodData.php");

            Food returnedFood = null;

            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse httpResponse = client.execute(post);

                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);
                JSONObject jObject = new JSONObject(result);

                if (jObject.length() != 0){
                    Log.v("search food", "3");
                    String name = jObject.getString("nomP");
                    String description = jObject.getString("descriptionP");
                    double price = jObject.getDouble("prixP");
                    String image = jObject.getString("imgP");
                    int qty = jObject.getInt("quantiteP");
                    int type = jObject.getInt("typeP");

                    returnedFood = new Food(name, description, price, image, qty, type);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return returnedFood;
        }

        @Override
        protected void onPostExecute(Food returnedFood) {
            super.onPostExecute(returnedFood);
            progressDialog.dismiss();
            foodCallBack.done(returnedFood);
        }
    }
}
