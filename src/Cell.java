import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by anchung on 12/26/16.
 */
class Cell {
    private int m_globalX;
    private int m_globalY;
    private int m_rowIndex;
    private int m_colIndex;
    private int m_boxIndex;

    private static final int BOX_SPAN = 3;

    private ArrayList<String> m_row;
    private ArrayList<String> m_col;
    private ArrayList<String> m_box;

    private String m_value;
    private Constraint m_possibles= null;

    Cell(int x, int y) {
        m_globalX = x;
        m_globalY = y;

        m_row = new ArrayList<>();
        m_col = new ArrayList<>();
        m_box = new ArrayList<>();

        m_possibles = new Constraint();
    }

    boolean evaluate() {
        if (!m_value.equals("-")) {
            return false;
        }

        String value = m_value;
        m_possibles.evaluate();
        ArrayList<String> values = m_possibles.getConstraints();
        if (values.size() == 1) {
            value = values.get(0);
            m_row.set(m_rowIndex, value);
            m_col.set(m_colIndex, value);
            m_box.set(m_boxIndex, value);
        }
        m_value = value;
        return true;
    }

    void grabRow(ArrayList<ArrayList<String>> rowArr) {
        m_row = rowArr.get(m_globalY);
        m_rowIndex = m_globalX;
        set_value();
    }

    void grabCol(ArrayList<ArrayList<String>> colArr) {
        m_col = colArr.get(m_globalX);
        m_colIndex = m_globalY;
        set_value();
    }

    void grabBox(ArrayList<ArrayList<String>> boxArr) {
        //box1, box2, box3
        //box4, ... , ...
        //..., ...., box9
        int x = m_globalX / BOX_SPAN;
        int y = m_globalY / BOX_SPAN;
        int box_index = y*BOX_SPAN + x;

        m_box = boxArr.get(box_index);

        x = m_globalX % BOX_SPAN;
        y = m_globalY % BOX_SPAN;
        m_boxIndex = y*BOX_SPAN + x;

        set_value();
    }

    private void set_value() {
        if (m_row.isEmpty()  || m_col.isEmpty() || m_box.isEmpty()) {
            return;
        }

        String value = m_row.get(m_rowIndex);
        assert (m_col.get(m_colIndex).equals(value) &&
            m_box.get(m_boxIndex).equals(value));

        m_value = value;
    }

    void show() {
        if (m_box.isEmpty()) {
            System.out.println("Box data not set");
            return;
        }
        if (m_col.isEmpty()) {
            System.out.println("Column data not set");
            return;
        }
        if (m_row.isEmpty()) {
            System.out.println("Row data not set");
        }

        System.out.format("cell (%d, %d): %s\n", m_globalX, m_globalY, m_value );
        showPossibleValues();
    }

    void showPossibleValues() {
        if (m_possibles == null) {
            System.out.println("Cell is not initialized.");
            return;
        }
        m_possibles.showConstraints();
    }

    private class Constraint {
        ArrayList<Integer> m_constraints;
        Integer m_value;

        Constraint() {
        };

        void evaluate() {
            m_constraints = new ArrayList<Integer>();

            ArrayList<String> inputs = new ArrayList<>(
                    Arrays.asList("1","2","3","4","5","6","7","8","9"));

            ArrayList<String> values = inputs.stream()
                    .filter(elem -> !m_row.contains(elem))
                    .filter(elem -> !m_col.contains(elem))
                    .filter(elem -> !m_box.contains(elem))
                    .collect(Collectors.toCollection(ArrayList::new));

            m_constraints = values.stream()
                    .map(elem -> convert(elem))
                    .collect(Collectors.toCollection(ArrayList::new));

        }

        Integer convert(String str) {
            try {
                return new Integer(str);
            } catch (NumberFormatException e) {
                //ignore
            }
            return new Integer(-1);
        }

        String convert(Integer val) {
            if (val.intValue() == -1) {
                return "-";
            }
            return val.toString();
        }

        ArrayList<String> getConstraints() {
             ArrayList<String> str = m_constraints.stream()
                    .map(elem -> convert(elem))
                    .collect(Collectors.toCollection(ArrayList::new));

             return str;
        }

        void showConstraints() {
            ArrayList<String> str = getConstraints();
            for (String val : str) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }
}
