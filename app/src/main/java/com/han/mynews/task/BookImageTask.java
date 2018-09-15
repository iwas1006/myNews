package com.han.mynews.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.han.mynews.adapter.BooksAdapter;
import com.han.mynews.dao.NewsIconDaoImpl;
import com.han.mynews.dto.Book;
import com.han.mynews.dto.OGTag;
import com.han.mynews.util.Util;

public class BookImageTask extends AsyncTask<Book, Void, String> {

    private Exception e;
    private Context c;
    private BooksAdapter booksAdapter;

    public BookImageTask(Context c, BooksAdapter booksAdapter) {
        this.c = c;
        this.booksAdapter = booksAdapter;
    }

    @Override
    protected String doInBackground(Book... books) {
        Log.d("__BookImageTask__books[0]_1__", books[0].toString());
        Util u = new Util();
        try {
            OGTag og = u.getOGTag(books[0].getUrl());
            if(og != null && og.getImage() != null && og.getImage().length() > 0) {
                if(og.getImage().startsWith("//")) {
                    books[0].setImageUrl("http:"+og.getImage());
                } else {
                    if(og.getImage() != null && og.getImage().trim().length() > 0) {
                        books[0].setImageUrl(og.getImage());
                        NewsIconDaoImpl newsIcon = new NewsIconDaoImpl(c);
                        newsIcon.insert(books[0].getUrl(), og.getImage());
                    }

                }

                Log.d("__BookImageTask__books[0]___", books[0].toString());

                return books[0].getImageUrl();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

       /* if(books[0].getImageUrl() == null || books[0].getImageUrl().trim().length() == 0) return null;

        try {
            URL url = new URL(books[0].getImageUrl());
            FileCache fc = new FileCache(c);

            try {
                File f = new File(url.getFile());

                URLConnection connection = url.openConnection();
                InputStream in = connection.getInputStream();
                FileOutputStream fos = new FileOutputStream(f);
                byte[] buf = new byte[512];
                while (true) {
                    int len = in.read(buf);
                    if (len == -1) {
                        break;
                    }
                    fos.write(buf, 0, len);
                }
                in.close();
                fos.flush();
                fos.close();

                // url.get
                if(f.exists()) {
                    Log.d("fileName3113____", "f.exists()");
                } else {
                    Log.d("fileName3113____", "!f.exists()");
                }

                String fileName = fc.writeFile(fc.makeFileName(books[0].getImageUrl()), f);


                File ff = fc.readFile(fileName);
                Log.d("fffffffffff____", ff.length()+"");
                this.imageView.setImageURI(Uri.fromFile(ff));
            } catch (FileNotFoundException e1) {
            } catch (IOException e1) {
                //    } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }


            return books[0].getImageUrl();
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        }*/
        return null;
    }

    @Override
    protected void onPostExecute(String string) {
        if(string == null || string.trim().length() == 0) return;
       // if(imageView == null) return;
       // imageView.setImageURI(Uri.parse(string));
        if(booksAdapter != null) booksAdapter.notifyDataSetChanged();
        //Log.d("ogTagUrl___", string);
        //Picasso picasso = PicassoProvider.   .get();




    }
}