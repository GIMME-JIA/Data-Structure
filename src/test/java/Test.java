public class Test {

    public static void main(String[] args) {
        int capacity = 66;
        capacity--;
        capacity |= capacity >> 1;
        capacity |= capacity >> 2;
        capacity |= capacity >> 4;
        capacity |= capacity >> 8;
        capacity |= capacity >> 16;
        capacity++;

        System.out.println(capacity);
    }
}
