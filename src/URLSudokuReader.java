import java.io.*;
import java.net.URL;

/**
 * Created by anchung on 12/24/16.
 */
class URLSudokuReader {

    String m_URL;
    URLSudokuReader(String url)
    {
        m_URL = url;
    }

    void read() throws IOException {
        BufferedReader in = null;
        PrintWriter outputStream = null;

        //http://show.websudoku.com
        URL myURL = new URL(m_URL);

        in = new BufferedReader(new InputStreamReader(myURL.openStream()));
        outputStream = new PrintWriter(new FileWriter("sudokuEasy1.html"));

        String inputLine;
        while((inputLine = in.readLine()) != null) {
            outputStream.println(inputLine);
        }

        if (in != null) {
            in.close();
        }
        if (outputStream != null) {
            outputStream.close();
        }
    }
}
