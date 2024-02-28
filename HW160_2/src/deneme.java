import java.util.ArrayList;

public class deneme{
    public static void main(String[] args) {
        ArrayList<String> l = new ArrayList<>();
        l.add("a");l.add("b");
        for (int i =0;i < l.size();i++){
            l.add("e");
        }
        System.out.println(l.size());
    }
}