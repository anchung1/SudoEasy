import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by anchung on 12/24/16.
 */
class URLParser {
    private ArrayList<String> m_parsedData;
    private Document m_doc;

    URLParser() {
    };

    void inFileName(String fileName) throws IOException {
        setupInternal(fileName);
    }

    void parseIt() {
        parse();
    }

    ArrayList<String> data() {
        return m_parsedData;
    }

    void show() {
        printCells(m_parsedData);
    }

    private void setupInternal(String fileName) throws  IOException {
        File input = new File(fileName);
        m_doc = Jsoup.parse(input, "UTF-8");
    }

    private void parse() {
        if (m_doc == null) return;

        Element table = m_doc.getElementById("puzzle_grid");

        m_parsedData = new ArrayList<String>();
        ArrayList<Element> elemInput = new ArrayList<Element>();
        recurseElements(table, elemInput);

        dataToString(elemInput);
    }

    private void recurseElements(Element parent, ArrayList<Element> elemInput) {
        if (parent.children().size() > 0) {
            for (Element child : parent.children()) {
                recurseElements(child, elemInput);
            }
        }

        //FIFO - (0,0) data on top
        if (parent.tagName().equals("input")) {
            elemInput.add(0,parent);
        }
    }

    private void dataToString(ArrayList<Element> elemInput) {
        for (Element elem : elemInput) {
            if (elem.hasAttr("VALUE")) {
                for (Attribute attrib : elem.attributes()) {
                    if (attrib.getKey().equals("VALUE") ) {
                        m_parsedData.add(attrib.getValue());
                        break;
                    }
                }
            } else {
                m_parsedData.add("-");
            }
        }
    }

    private void printCells(ArrayList<String> elemInput) {
        System.out.println("container has " + elemInput.size() + " elements");

        int count = 0;
        for (String elem : elemInput) {
            System.out.print(elem + " ");

            if ( (++count % 9) == 0 ) {
                System.out.println();
            }
        }
        System.out.println();
    }
}
