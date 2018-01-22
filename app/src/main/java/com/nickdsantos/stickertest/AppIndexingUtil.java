package com.nickdsantos.stickertest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.appindexing.FirebaseAppIndex;
import com.google.firebase.appindexing.FirebaseAppIndexingInvalidArgumentException;
import com.google.firebase.appindexing.Indexable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by nicknux on 1/21/18.
 */

public class AppIndexingUtil {
    private static final String STICKER_FILENAME_PATTERN = "sticker%s.png";
    private static final String STICKER_PACK_URL_PATTERN = "mystickers://sticker/pack/%s";
    private static final String STICKER_PACK_NAME = "Sticker Test Content Pack";
    private static final String TAG = "AppIndexingUtil";
    public static final String FAILED_TO_INSTALL_STICKERS = "Failed to install stickers";

    public static void setStickers(final Context context, FirebaseAppIndex firebaseAppIndex) {
        try {
            Indexable stickerPack = getIndexableStickerPack(context);

            List<Indexable> indexables = new ArrayList<>();
            indexables.add(stickerPack);

            Task<Void> task = firebaseAppIndex.update(
                    indexables.toArray(new Indexable[indexables.size()]));

            task.addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(context, "Successfully added stickers", Toast.LENGTH_SHORT)
                            .show();
                }
            });

            task.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, FAILED_TO_INSTALL_STICKERS, e);
                    Toast.makeText(context, FAILED_TO_INSTALL_STICKERS, Toast.LENGTH_SHORT)
                            .show();
                }
            });
        } catch (IOException | FirebaseAppIndexingInvalidArgumentException e) {
            Log.e(TAG, "Unable to set stickers", e);
        }
    }

    private static Indexable getIndexableStickerPack(Context context)
            throws IOException, FirebaseAppIndexingInvalidArgumentException {

        List<Indexable> stickers = getIndexableStickers(context);
        Indexable.Builder stickerPackBuilder = new Indexable.Builder("StickerPack")
                .setName(STICKER_PACK_NAME)
                .setUrl(String.format(STICKER_PACK_URL_PATTERN, 0))
                .setDescription("Sample Sticker Pack")
                .put("hasSticker", stickers.toArray(new Indexable[stickers.size()]));

        return stickerPackBuilder.build();
    }

    private static List<Indexable> getIndexableStickers(Context context) throws IOException,
            FirebaseAppIndexingInvalidArgumentException {
        List<Indexable> indexableStickers = new ArrayList<>();
        try {
            readStickers(context, indexableStickers);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        return indexableStickers;
    }

    private static String getStickerFilename(int index) {
        return String.format(STICKER_FILENAME_PATTERN, index);
    }

    private static void readStickers(Context context, List<Indexable> indexableStickers)
            throws FirebaseAppIndexingInvalidArgumentException, ParserConfigurationException, IOException, SAXException {
        InputStream is = context.getResources().openRawResource(R.raw.stickers);
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document doc = builder.parse(is);

        NodeList nodes = doc.getElementsByTagName("sticker");
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) node;

                Indexable.Builder stickerBuilder = new Indexable.Builder("Sticker")
                        .setName(elem.getAttribute("name"))
                        .setImage(elem.getAttribute("url"))
                        .setUrl(elem.getAttribute("url"))
                        .setDescription(elem.getAttribute("description"))
                        .put("keywords", elem.getAttribute("keywords"))
                        .put("isPartOf",
                                new Indexable.Builder("StickerPack")
                                        .setName(STICKER_PACK_NAME).build());

                indexableStickers.add(stickerBuilder.build());
            }
        }
    }
}
