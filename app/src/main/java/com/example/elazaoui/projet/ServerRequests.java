package com.example.elazaoui.projet;

/**
 * Created by elazaoui on 02/03/16.
 */



import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;


public class ServerRequests {
    ProgressDialog progressDialog;
    public static final int CONNECTION_TIMEOUT = 1000 * 15;
    public static final String SERVER_ADDRESS = "http://shareurfood.esy.es/";

    public ServerRequests(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing...");
        progressDialog.setMessage("Please wait...");
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


    public class StoreFoodDataAsyncTask extends AsyncTask<Void, Void, Void> {
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
                /*String username = "coco";
                String nomP = "myplat";
                String descriptionP = "itsgood";
                double prixP = 6.3;
                int fk_user = 12;
                String imageP = "testlien";
                int quantiteP = 5;
                int typeP = 2;*/
              // cette syntaxe en commentaire ne marche pas, il faut trouver une autre comme celle qui marche
              // String link = "http://shareurfood.esy.es/CreateFood.php?username=" + username + "&nomP=" + nomP + "&descriptionP=" + descriptionP + "&prixP=" + prixP + "&fk_user=" + fk_user + "&imageP" + imageP + "&quantiteP=" + quantiteP + "&typeP=" + typeP;
String link = "http://shareurfood.esy.es/CreateFood.php?username=%22coco%22&nomP=%22myplatandroidversionusername%22&descriptionP=%22descriptionPro%22&prixP=5&imageP=%22test%22&quantiteP=5&typeP=3";
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
           /* ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("nomP", "myfood"));
            dataToSend.add(new BasicNameValuePair("descriptionP", "i love it"));
            dataToSend.add(new BasicNameValuePair("prixP", "5.0"));
            dataToSend.add(new BasicNameValuePair("fk_user", "12"));
            dataToSend.add(new BasicNameValuePair("imageP", "liencase"));
            dataToSend.add(new BasicNameValuePair("quantiteP", "4"));
            dataToSend.add(new BasicNameValuePair("typeP", "2"));
            HttpParams httpRequestParams = getHttpRequestParams();

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS
                    + "CreateFood.php");

            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                client.execute(post);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }*/

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
                    Log.v("happened", "2");
                    String name = jObject.getString("name");
                    int age = jObject.getInt("age");
                    String adresse = jObject.getString("adresse");
                    String arrondissement = jObject.getString("arrondissement");
                    returnedUser = new User(name, age, user.username,user.password,adresse, arrondissement);
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
                    Log.v("show list food", "3");
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