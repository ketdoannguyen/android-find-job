package com.android.app_findjob.Rss;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ReadRSS extends AsyncTask<String, Void, String>
{
    ProgressDialog progressDialog;
    Blog_RSS context;
    ListView listView;
    public ReadRSS(Context context, ListView listView) {
        this.context = (Blog_RSS) context;
        this.listView = listView;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Thông báo");
        progressDialog.setMessage("Đang xử lý...");
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {
        StringBuilder content=new StringBuilder();
        try {
            URL url=new URL(strings[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line=br.readLine())!=null)
                {
                    content.append(line);
                }
                br.close();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        XMLDOMParser Parser = new XMLDOMParser();
        Document document = Parser.getDocument(s);
        NodeList nodeList = document.getElementsByTagName("item");
        String tieuDe = "";
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            tieuDe = Parser.getValue(element, "title");
            context.arrayTitle.add(tieuDe);
            context.arrayLink.add(Parser.getValue(element,"link"));
            context.listView.setAdapter(context.adapter);
        }
        context.adapter.notifyDataSetChanged();
        progressDialog.dismiss();
    }
}
