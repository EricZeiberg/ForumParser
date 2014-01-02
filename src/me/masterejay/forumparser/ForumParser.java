package me.masterejay.forumparser;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author MasterEjay
 */
public class ForumParser {

    static List<String> titleList = new ArrayList<String>();



    public static void start() throws Exception
    {
        URL url = new URL("http://open.dapper.net/RunDapp?dappName=OCNForums&v=1&applyToUrl=https%3A%2F%2Foc.tc%2Fforums&filter=true");
        URLConnection connection = url.openConnection();

        Document doc = parseXML(connection.getInputStream());
        NodeList descNodes = doc.getElementsByTagName("Thread");


        for(int i=0; i<descNodes.getLength();i++)
        {
            Node node = descNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                System.out.println("Thread Title: " + getValue("Post_Title", element));
                if (!titleList.contains(getValue("Post_Title", element))){
                    titleList.add(getValue("Post_Title", element));
                }
            }
        }
    }

    private static Document parseXML(InputStream stream)
            throws Exception
    {
        DocumentBuilderFactory objDocumentBuilderFactory = null;
        DocumentBuilder objDocumentBuilder = null;
        Document doc = null;
        try
        {
            objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
            objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();

            doc = objDocumentBuilder.parse(stream);
        }
        catch(Exception ex)
        {
            throw ex;
        }

        return doc;
    }

    private static String getValue(String tag, Element element) {
        NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodes.item(0);
        return node.getNodeValue();
    }

    public static void setupSchedule(){
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Runnable toRun = new Runnable() {
            public void run() {
                try {
                    start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        ScheduledFuture<?> handle = scheduler.scheduleAtFixedRate(toRun, 1, 1, TimeUnit.MINUTES);
    }
}

