import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.security.InvalidParameterException;
import static org.junit.jupiter.api.Assertions.*;

class MatrixTest {
    private double[][] test = {
            {2, 2, 4, 4},
            {8, 2, 4, 10},
            {8, 4, 4, 6},
            {4, 12, 6, 6}
    };

    @Test @DisplayName("Test constructor without params")
    void constructorsWithoutParams() {
        assertArrayEquals(new double[1][1], new Matrix().getArray());
    }

    @Test @DisplayName("Test constructor with size")
    void constructorWithSize() {
        assertAll("constructorWithSize",
                () -> assertArrayEquals(new double[][]{{0, 0}, {0, 0}}, new Matrix(2).getArray()),
                () -> assertEquals("Invalid Matrix Dimension", assertThrows(InvalidParameterException.class, () -> new Matrix(0)).getMessage()),
                () -> assertEquals("Invalid Matrix Dimension",assertThrows(InvalidParameterException.class, () -> new Matrix(-2)).getMessage())
        );
    }

    @Test @DisplayName("Test constructor with rows and cols")
    void constructorWithRowsAndCols() {
        assertAll("constructorWithRowsAndCols",
                () -> assertArrayEquals(new double[][]{{0, 0}, {0, 0}, {0, 0}}, new Matrix(3, 2).getArray()),
                () -> assertEquals("Invalid Matrix Dimension", assertThrows(InvalidParameterException.class, () -> new Matrix(0, 3)).getMessage()),
                () -> assertEquals("Invalid Matrix Dimension", assertThrows(InvalidParameterException.class, () -> new Matrix(1, -1)).getMessage())
        );
    }

    @Test @DisplayName("Test constructor with array")
    void constructorWithArray() {
        assertAll("constructorWithArray",
                () -> assertArrayEquals(test, new Matrix(test).getArray()),
                () -> assertEquals("Your array is not a matrix", assertThrows(InvalidParameterException.class, () -> new Matrix(new double[][]{{2, 5, 1}, {2}, {3, 2}, {3, 3}})).getMessage()),
                () -> assertEquals("Matrix is null",assertThrows(NullPointerException.class, () -> new Matrix(null)).getMessage()),
                () -> assertEquals("Your array is not a matrix",assertThrows(InvalidParameterException.class, () -> new Matrix(new double[][]{})).getMessage())
        );
    }

    @Test @DisplayName("Test getArray")
    void getArray() {
        assertArrayEquals(test, (new Matrix(test).getArray()));
    }

    @Test @DisplayName("Test getCols")
    void getCols() {
        assertEquals(test[0].length, (new Matrix(test).getCols()));
    }

    @Test @DisplayName("Test getRows")
    void getRows() {
        assertEquals(test.length, (new Matrix(test).getRows()));
    }

    @Test @DisplayName("Test toString")
    void ToString() {
        assertEquals("Matrix{rows=4, cols=4, data=[[2.0, 2.0, 4.0, 4.0]," +
                " [8.0, 2.0, 4.0, 10.0]," +
                " [8.0, 4.0, 4.0, 6.0]," +
                " [4.0, 12.0, 6.0, 6.0]]}", new Matrix(test).toString());
    }

    @Test @DisplayName("Test equals")
    void equals() {
        Matrix same = new Matrix(test);
        assertAll("equals",
                () -> assertEquals(same, same),
                () -> assertNotEquals(null, new Matrix(test)),
                () -> assertEquals(new Matrix(test), new Matrix(new double[][]{{2, 2, 4, 4}, {8, 2, 4, 10}, {8, 4, 4, 6}, {4, 12, 6, 6}})),
                () -> assertNotEquals(new Matrix(test), new Matrix())
        );
    }

    @Test @DisplayName("Test divide matrix")
    void divide() {
        double[][] expectedDivided1 = {
                {1, 1, 2, 2},
                {4, 1, 2, 5},
                {4, 2, 2, 3},
                {2, 6, 3, 3}
        };
        double[][] expectedDivided2 = new double[test.length][test[0].length];
        double[][] expectedDivided3 = new double[test.length][test[0].length];
        for (int i = 0; i < test.length; i++)
            for (int j = 0; j < test[0].length; j++) {
                expectedDivided2[i][j] = test[i][j] / 3.0;
                expectedDivided3[i][j] = test[i][j] / -1;
            }

        assertAll("divide",
                () -> assertEquals("Can not divide on zero", assertThrows(InvalidParameterException.class, () -> new Matrix().divide(0)).getMessage()),
                () -> assertEquals(new Matrix(test), new Matrix(test).divide(1)),
                () -> assertEquals(new Matrix(expectedDivided1), new Matrix(test).divide(2)),
                () -> assertEquals(new Matrix(expectedDivided2), new Matrix(test).divide(3)),
                () -> assertEquals(new Matrix(expectedDivided3), new Matrix(test).divide(-1))
        );
    }

    @Test @DisplayName("Test diff matrix")
    void diff() {
        double[][] expectedRes = {
                {1, 1, 3, 3},
                {7, 1, 3, 9},
                {7, 3, 3, 5},
                {3, 11, 5, 5}
        };
        double[][] arr = {
                {1, 1, 1, 1},
                {1, 1, 1, 1},
                {1, 1, 1, 1},
                {1, 1, 1, 1}
        };
        assertAll("diff",
                () -> assertEquals("Can not subtract null matrix", assertThrows(NullPointerException.class, () -> new Matrix(test).diff(null)).getMessage()),
                () -> assertEquals(new Matrix(test.length), new Matrix(test).diff(new Matrix(test))),
                () -> assertEquals(new Matrix(test), new Matrix(test).diff(new Matrix(test.length))),
                () -> assertEquals(new Matrix(expectedRes), new Matrix(test).diff(new Matrix(arr))),
                () -> assertEquals("Matrices must be of the same dimension", assertThrows(InvalidParameterException.class, () -> new Matrix(test).diff(new Matrix())).getMessage())
        );
    }

    @Test @DisplayName("Test determinant matrix")
    void determinant() {
        assertAll("determinant",
                () -> assertEquals(0, new Matrix().determinant()),
                () -> assertEquals(0, Matrix.determinant(new Matrix(4))),
                () -> assertEquals("Matrix is null", assertThrows(NullPointerException.class, () -> Matrix.determinant(null)).getMessage()),
                () -> assertEquals("Matrix must be square", assertThrows(InvalidParameterException.class, () -> new Matrix(new double[][]{{0}, {0}}).determinant()).getMessage()),
                () -> assertEquals(816, new Matrix(test).determinant())
        );
    }
}