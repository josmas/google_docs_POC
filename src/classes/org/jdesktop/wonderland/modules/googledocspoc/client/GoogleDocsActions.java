package org.jdesktop.wonderland.modules.googledocspoc.client;

import com.google.gdata.client.DocumentQuery;
import com.google.gdata.client.docs.DocsService;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.docs.DocumentEntry;
import com.google.gdata.data.docs.DocumentListEntry;
import com.google.gdata.data.docs.DocumentListFeed;
import com.google.gdata.data.media.MediaByteArraySource;
import com.google.gdata.data.media.MediaMultipart;
import com.google.gdata.util.ServiceException;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

/**
 * Creation, search, and update of Google Word Processor documents
 * @author jos
 */
public class GoogleDocsActions {

    private static final String PRIVATE_DOCS_URL =
            "https://docs.google.com/feeds/default/private/full/";
    private DocsService client;
    URL feedUri = new URL(PRIVATE_DOCS_URL);

    public GoogleDocsActions() throws IOException, ServiceException {
        client = new DocsService("openwonderland-poc-v1"); //This id should be personalised
        //TODO enter your credentials here: your_name@gmail.com and your password
        client.setUserCredentials("xxxxx@gmail.com", "your_password");
    }

    public DocumentListEntry createNewWordProcessorDocument(String title, String content)
            throws IOException, ServiceException {
        DocumentListEntry newEntry = null;
        newEntry = new DocumentEntry();
        newEntry.setTitle(new PlainTextConstruct(title));
        client.insert(feedUri, newEntry);

        // For some reason related to shared docs we cannot add content to a doc
        // without retrieving it first
        DocumentListEntry savedEntry = findOneDoc(title);

        return updateDocumentWithContent(savedEntry, content);
    }
    
    private DocumentListEntry findOneDoc(String textForSearch) throws IOException, ServiceException {

        DocumentQuery query = new DocumentQuery(feedUri);
        query.setTitleQuery(textForSearch);
        query.setTitleExact(false);
        query.setMaxResults(1);
        DocumentListFeed feed = client.getFeed(query, DocumentListFeed.class);

        return feed.getEntries().get(0);
    }

    private DocumentListEntry updateDocumentWithContent(DocumentListEntry savedEntry, String content)
            throws IOException, ServiceException{
        // When using the Data API from an environment other than a Desktop application
        // MIME types are handled in a somehow inconsistent manner. The fix used here
        // with the classloaders was taken from:
        // http://nexnet.wordpress.com/2010/06/26/google-docs-api-unsupporteddatatypeexception-mime-type-applicationatomxml/
        Thread cur = Thread.currentThread();
        ClassLoader ccl = cur.getContextClassLoader();
        ClassLoader classLoader = this.getClass().getClassLoader();
        cur.setContextClassLoader(classLoader);

        MediaMultipart.loadMimeMappings();

        savedEntry.setMediaSource(new MediaByteArraySource(content.getBytes(), "text/plain"));
        savedEntry.setEtag("*");

        DocumentListEntry updatedEntry = savedEntry.updateMedia(true);
        cur.setContextClassLoader(ccl);

        return updatedEntry;
    }
}
