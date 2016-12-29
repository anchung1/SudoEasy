import java.util.ArrayList;

/**
 * Created by anchung on 12/26/16.
 */
class Sudoku {
    private ArrayList<ArrayList<String>> m_rowData;
    private ArrayList<ArrayList<String>> m_colData;
    private ArrayList<ArrayList<String>> m_boxData;
    private ArrayList<Cell> m_cells;

    private static final int MAX_ROWS = 9;
    private static final int MAX_COLS = 9;
    private static final int MAX_BOXES = 9;

    Sudoku() {

    }

    void boardData(ArrayList<String> bdata) throws SudoException {
        if (bdata.size() != MAX_ROWS * MAX_COLS) {
            throw new SudoException("Dimension mismatch");
        }

        populateRows(bdata);
        populateCols(bdata);
        populateBoxes(bdata);
        populateCells();
    }

    void play() {
        boolean delta = true;
        while (delta) {
            delta = false;
            for (Cell cell : m_cells) {
                delta |= cell.evaluate();
            }
            if (delta) {
                show();
            }
        }
    }

    void show() {
        showData(m_rowData);
    }

    void showRowData() {
        showData(m_rowData);
    }

    void showColData() {
        showData(m_colData);
    }

    void showBoxData() {
        showData(m_boxData);
    }

    void showAllCells() {
        for (int x=0; x<MAX_COLS; x++) {
            for (int y=0; y<MAX_ROWS; y++) {
                showCell(x, y);
            }
        }
    }
    void showCell(int x, int y) {
        Cell cell = m_cells.get(y*MAX_COLS + x);
        cell.show();
    }

    private void showData(ArrayList<ArrayList<String>> data) {
        for (ArrayList<String> elem : data) {
            for (String val : elem) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private void populateRows(ArrayList<String> bdata) {
        m_rowData = new ArrayList<>(MAX_ROWS);

        for (int i=0; i<MAX_ROWS; i++) {
            ArrayList<String> row = new ArrayList<>();
            for (int j=0; j<MAX_COLS; j++) {
                row.add(bdata.get(i*MAX_COLS + j));
            }
            m_rowData.add(row);
        }
    }

    private void populateCols(ArrayList<String> bdata) {
        m_colData = new ArrayList<>(MAX_COLS);

        for (int i=0; i<MAX_COLS; i++) {
            m_colData.add( new ArrayList<String>(MAX_COLS));
        }

        for (int i=0; i<MAX_ROWS; i++) {
            for (int j=0; j<MAX_COLS; j++) {
                ArrayList<String> col = m_colData.get(j);
                col.add(bdata.get(i*MAX_COLS + j));
            }
        }
    }

    private void populateBoxes(ArrayList<String> bdata) {
        m_boxData = new ArrayList<>(MAX_BOXES);

        Box box = new Box(bdata);
        for (int i=0; i<MAX_BOXES; i++) {
            m_boxData.add( box.createBox(i));
        }

    }

    private class Box {
        private int m_span= 3;
        private int[] m_offset = {
                0, 3, 6,
                3*MAX_COLS, 3*MAX_COLS+3, 3*MAX_COLS+6,
                6*MAX_COLS, 6*MAX_COLS+3, 6*MAX_COLS+6
        };
        private ArrayList<String> m_data;

        Box(ArrayList<String> data) {
            m_data = data;
        };

        ArrayList<String> createBox(int index) {
            ArrayList<String> box = new ArrayList<>();

            int offset = m_offset[index];

            for (int i=0; i<m_span; i++) {
                for (int j=0; j<m_span; j++) {
                    box.add(m_data.get(offset+j));
                }
                offset += MAX_COLS;
            }

            return box;
        }

    }

    private void populateCells() {
        m_cells = new ArrayList<Cell>();

        for (int i=0; i<MAX_ROWS; i++) {
            for (int j=0; j<MAX_COLS; j++) {
                Cell cell = new Cell(j, i);
                cell.grabRow(m_rowData);
                cell.grabCol(m_colData);
                cell.grabBox(m_boxData);
                m_cells.add(cell);
            }
        }
    }

}
