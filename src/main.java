import java.util.ArrayList;
import java.util.Scanner;


public class main {

    public static void main (String[] args) {
        System.out.println("请将屏蔽词列表重命名为”屏蔽词.xml“，并放在当前工作路径下");
        while (true) {
            System.out.println("请拖入文件");
            Scanner sc = new Scanner(System.in);
            String file_path = sc.nextLine();
            if (file_path.contains("\""))
                file_path = file_path.substring(1, file_path.length() - 1);
            System.out.println(file_path);

            file_operation fo = new file_operation();
            flitter f = new flitter();

            ArrayList<String> barrage_list = fo.readFile(file_path, 0);
            barrage_list = f.heightFlit(barrage_list);
            barrage_list = f.shieldFlit(barrage_list);
            barrage_list = f.sizeChange(barrage_list);
            fo.writeFile(file_path, barrage_list);

        }
    }
}
