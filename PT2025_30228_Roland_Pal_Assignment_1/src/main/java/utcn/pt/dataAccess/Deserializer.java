package utcn.pt.dataAccess;

import utcn.pt.dataModel.Employee;
import utcn.pt.dataModel.Task;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Deserializer {
    public static Map<Employee, List<Task>> loadDate(String filename){
        Map<Employee, List<Task>> data = null;

        try {
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fileIn);

            data = (Map<Employee, List<Task>>) in.readObject();
            System.out.println("Data has been deserialized!\n");

        } catch(Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    public static ArrayList<Task> loadDateList(String filename){
        ArrayList<Task> data = null;

        try {
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fileIn);

            data = (ArrayList<Task>) in.readObject();
            System.out.println("Data has been deserialized!\n");

        } catch(Exception e) {
            e.printStackTrace();
        }

        return data;
    }
}
