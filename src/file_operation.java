import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static java.lang.System.exit;

public class file_operation {
    // read barrages from ass files
    public static ArrayList<String> readFile(String file_name, int flag){
        ArrayList<String> barrage_list = new ArrayList<>();
        try {
            FileInputStream file = new FileInputStream(file_name);
            BufferedReader buff = new BufferedReader(new InputStreamReader(file, StandardCharsets.UTF_8));
            //read
            String temp;
            temp = buff.readLine();
            temp = temp.substring(1);
            barrage_list.add(temp);
            while((temp = buff.readLine()) != null)
            {
                barrage_list.add(temp);
            }
            //close
            file.close();
            buff.close();
        }
        catch (IOException e){
            if (flag == 0) {
                System.out.println("未找到指定文件，或文件类型错误");
                exit(0);
            }
            else
                System.out.println("无屏蔽词");
        }
        return barrage_list;
    }

    //write modified file
    public static void writeFile(String path, ArrayList<String> barrage_list){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(path + ".ass"));
            for (String line : barrage_list){
                bw.write(line);
                bw.newLine();
                bw.flush();
            }
            bw.close();
            System.out.println("新文件创建成功");
        }
        catch (IOException e){
            System.out.println("创建文件失败");
        }
    }

}
