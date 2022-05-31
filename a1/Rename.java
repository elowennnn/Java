
import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class Rename {
    public static void main(String[] args){
        int flag_help = 0;
        if(args.length == 0){
            flag_help = 1;
            printHelp();
        } else {
            HashMap<String, ArrayList> options = parse(args); // source code from CS349 public
            ArrayList file_names = new ArrayList();
            ArrayList file_values = new ArrayList();
            String all_prefix = new String();
            String all_suffix = new String();
            String ori_replace = new String();
            String new_replace = new String();
            int flag = 0;
            int flag_file = 0;
            int flag_pre = 0;
            int flag_suf = 0;
            int flag_rep = 0;

            String date = new String();
            date = LocalDate.now().toString();
            date = reverse_date(date);
            String time = new String();
            time = LocalTime.now().toString().substring(0,8);
            time = time.replace(":","-");

            for (Map.Entry entry : options.entrySet()){ // source code from CS349 public
                String key = (String) entry.getKey();
                ArrayList values = (ArrayList) entry.getValue();

                if (key.equals("h") || key.equals("help")) {
                    // print out the help menu
                    flag_help = 1;
                    printHelp();

                } else if (key.equals("p") || key.equals("prefix")) {
                    // change file prefix
                    if (values.size() == 0) System.out.println("Valid Option -prefix, but required value is missing");
                    for (int i = values.size() - 1; i >= 0; i--) {
                        if (values.get(i).equals("@time")) all_prefix = all_prefix.concat(time);
                        else if (values.get(i).equals("@date")) all_prefix = all_prefix.concat(date);
                        else all_prefix = all_prefix.concat((String) values.get(i));
                    }
                    flag_pre = 1;

                } else if (key.equals("s") || key.equals("suffix")) {
                    // change file suffix
                    if (values.size() == 0) System.out.println("Valid Option -suffix, but required value is missing");
                    for (int i = 0; i < values.size(); i++) {
                        if (values.get(i).equals("@time")) all_suffix = all_suffix.concat(time);
                        else if (values.get(i).equals("@date")) all_suffix = all_suffix.concat(date);
                        else all_suffix = all_suffix.concat((String) values.get(i));
                    }
                    flag_suf = 1;

                } else if (key.equals("r") || key.equals("replace")) {
                    if (values.size() == 0 || values.size() == 1) {
                        flag_rep = 1;
                        System.out.println("Valid Option -replace, but required value is missing");
                    }
                        // change entire filename
                    else {
                        ori_replace = ((String) values.get(0));
                        if (ori_replace.equals("@date")) ori_replace = date;
                        if (ori_replace.equals("@time")) ori_replace = time;
                        flag = 1;
                        flag_rep = 1;
                        if (values.get(1).equals("@date"))  {
                            new_replace = date;
                        }
                        else if (values.get(1).equals("@time")){
                            new_replace = time;
                        } else {
                            new_replace = (String) values.get(1);
                        }
                    }

                } else if (key.equals("f") || key.equals("file")) {
                    if (values.size() != 0) flag_file = 1;
                    for (int i = 0; i < values.size(); i++){
                        file_names.add(i, (values.get(i)));
                        file_values.add(i, (values.get(i)));
                    }

                    for (int i = 0; i < values.size(); i++){
                        String element = (String) values.get(i);
                        File ff = new File((String) values.get(i));

                        if (!ff.exists()) file_names.set(i, "not exists");
                    }
                }
                else {
                    System.out.println("Error: Invalid Option: -" + key);
                }
            }
                // rename the file
            for (int i = 0; i < file_values.size(); i++) {
                if (file_names.size() != 0 && file_names.get(i).equals("not exists")) {
                    System.out.println("Error: file " + file_values.get(i) + " does not exist");
                } else {
                    if (flag == 1){
                        // replace ori with new
                        String old_n = file_values.get(i).toString();
                        String new_n = old_n.replace(ori_replace, new_replace);
                        File old_nnn = new File(old_n);
                        File new_nnn = new File(new_n);
                        if (!old_n.equals(new_n)) { // previous name and new name is not the same
                            if (new_nnn.exists()) System.out.println("Error: " + new_nnn + " already exists");
                            else {
                                old_nnn.renameTo(new_nnn);
                                System.out.println("Successfully Renamed " + old_n + " to " + new_n);
                            }
                        } else System.out.println("File " + old_n + " does not contain " + ori_replace);

                    } else {
                        if (flag_file == 1 &&  (flag_pre == 1 || flag_suf == 1)){
                            File old_name = new File((String) file_values.get(i));
                            String news = all_prefix + file_names.get(i) + all_suffix;
                            File new_name = new File(news);
                            if (new_name.exists()) System.out.println("Error: " + new_name + " already exists");
                            else {
                                old_name.renameTo(new_name);
                                System.out.println("Successfully Renamed " + old_name.toString() + " to " + new_name);
                            }
                        }
                    }
                }
            }

            if (flag_file == 0 && flag_help == 0) System.out.println("Error: No filename provided");
            if (flag_file == 1 && flag_pre == 0 && flag_suf == 0 && flag_rep == 0){
                System.out.println("Error: Filename provided, but no option specified");
            }
        }
    }

    static void printHelp(){
        System.out.println("(c) 2021 Yixue Zhang. Last revised: Jun 2, 2021.");
        System.out.println("Usage: rename [-option argument1 argument2 ...]");
        System.out.println("\nOptions:");
        System.out.println("-f|file [filename]          :: file(s) to change.");
        System.out.println("-p|prefix [string]          :: rename [filename] so that it starts with [string].");
        System.out.println("-s|suffix [string]          :: rename [filename] so that it ends with [string].");
        System.out.println("-r|replace [str1] [str2]    :: rename [filename] by replacing all instances of [str1] with [str2].");
        System.out.println("-h|help                     :: print out this help and exit the program.");

    }
    static HashMap<String, ArrayList> parse(String[] args){ // source code from CS349 public
        HashMap<String, ArrayList> arguments = new HashMap<>(); // source code from CS349 public
        String key = null; // source code from CS349 public
        ArrayList values = new ArrayList();

        for(String entry : args){ // source code from CS349 public
            if(entry.startsWith("-")){ // source code from CS349 public
                key = entry.substring(1); // source code from CS349 public
                values = new ArrayList();
            } else {
                values.add(entry);
            }
            if (key == null){
                System.out.println("Error: No options provided");
            }
            if (key != null && (key.equals("h") || key.equals("help"))){
                arguments.put(key, values);
                break;
            }
            if (key != null && values != null){
                arguments.put(key, values);
            }
        }
        return arguments;
    }
    static String reverse_date(String s){
        s = s.substring(5, 7)+ "-" +s.substring(8, 10) + "-" + s.substring(0,4);
        return s;
    }
}


