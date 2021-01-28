import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.*;


public class flitter {
    private static int default_height = 25;
    private static String default_shield_path = "屏蔽词.xml";
    private static double default_frontsize = 0.9;

    // 过滤弹幕高度
    public static ArrayList<String> heightFlit(ArrayList<String> barrage_list){
        Iterator<String> it = barrage_list.iterator();
        Scanner sc = new Scanner(System.in);

        System.out.println("输入保留百分比,默认为25 ");
        try{
            String height = sc.nextLine();
            if (!height.isEmpty()) {
                default_height = Integer.valueOf(height);
            }
        }
        catch (Exception e){
            System.out.println("错误输入格式，已采用默认值 \n");
        }
        while (it.hasNext()){
            String s = it.next();

            // 滚动型弹幕
            int move_position = s.indexOf("\\move");
            if (move_position != -1) {
                String s1 = s.substring(move_position);
                int comma_position = s1.indexOf(",");
                String s2 = s1.substring(comma_position + 2, s1.indexOf(",", s1.indexOf(",") + 1));
                int y_height = Integer.valueOf(s2);
                if (y_height > 1080 / 100 * default_height){
                    it.remove();
                }
            }

            // 居中型弹幕
            move_position = s.indexOf("\\pos");
            if (move_position != -1) {
                String s1 = s.substring(move_position);
                int comma_position = s1.indexOf(",");
                String s2 = s1.substring(comma_position + 2, s1.indexOf(")"));
                int y_height = Integer.valueOf(s2);
                if (y_height > 1080 / 100 * default_height){
                    it.remove();
                }
            }
        }
        return barrage_list;
    }


    // 过滤屏蔽词，基于b站导出的xml文件
    public static ArrayList<String> shieldFlit(ArrayList<String> barrage_list){
        file_operation fo = new file_operation();
        ArrayList<String> shield_list = fo.readFile(default_shield_path, 1);
        if (shield_list.size() != 0){
            for (String line: shield_list){
                // 普通表达
                int position = line.indexOf("t=");
                if (position != -1) {
                    String shield_word = line.substring(position + 2, line.indexOf("<",position));
                    Iterator<String> it = barrage_list.iterator();
                    while (it.hasNext()){
                        String s = it.next();
                        if (s.contains(shield_word))
                            it.remove();
                    }
                }

                // 正则
                int regex_position = line.indexOf("r=");
                if(regex_position != -1){
                    String shield_word = line.substring(regex_position + 2, line.indexOf("<",regex_position));
                    //System.out.println(shield_word);
                    Iterator<String> it = barrage_list.iterator();
                    while (it.hasNext()){
                        String s = it.next();
                        int br_position = s.indexOf('}');
                        if (br_position != -1) {
                            s = s.substring(br_position + 1);
                            if (s.matches(shield_word))
                                it.remove();
                        }
                    }
                }
            }
        }
        return barrage_list;
    }

    // 改变字体大小
    public static ArrayList<String> sizeChange(ArrayList<String> barrage_list){
        Scanner sc = new Scanner(System.in);
        Iterator<String> it = barrage_list.iterator();

        System.out.println("输入字体放大缩小倍率,默认为0.9倍 ");
        try{
            String size = sc.nextLine();
            if (!size.isEmpty()) {
                default_frontsize = Double.valueOf(size);
            }
        }
        catch (Exception e){
            System.out.println("错误输入格式，已采用默认值 \n");
        }
        double new_size = default_frontsize * 64;
        while (it.hasNext()){
            String line = it.next();
            if (line.contains("Style:") && line.contains("64")){
                String[] ss = line.split(",");
                ss[2] = String.valueOf(new_size);
                String temp = null;
                for (String x : ss){
                    if (temp == null)
                        temp = x;
                    else
                        temp += ", " + x;
                }
                barrage_list.set(barrage_list.indexOf(line), temp);
                break;
            }
        }
        return barrage_list;
    }
}
