package utcn.pt.dataAccess;

import utcn.pt.dataModel.Employee;
import utcn.pt.dataModel.Task;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Serializer {
    public static void saveDate(Map<Employee, List<Task>> data, String filename){
        try {
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(data);
            System.out.println("Data has been serialized and saved!\n");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveDateList(ArrayList<Task> data, String filename){
        try {
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(data);
            System.out.println("Data has been serialized and saved!\n");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
