package utcn.pt.businessLogic;

import utcn.pt.dataModel.Employee;
import utcn.pt.dataModel.Task;

import java.util.*;

public class Utility{
    TaskManagement myTaskManagement;

    public Utility(TaskManagement myTaskManagement){
        this.myTaskManagement = myTaskManagement;
    }

    public String filterEmployees(Map<Employee, List<Task>> map){
        TreeMap<Integer, Employee> myTree = new TreeMap<>(); // sa fie deja sortata dupa key

        StringBuilder result = new StringBuilder();

        for(Employee employee: map.keySet()){
            int empID = employee.getIdEmployee();
            int workD = myTaskManagement.calculateEmployeeWorkDuration(empID);
            if(workD > 40){
                myTree.put(workD, employee);
            }
        }

        result.append("Ordinea sortata a angajatilor dupa orele lucrate(>40):\n");
        for(Employee employee : myTree.values()){
            result.append(" - Angajatul cu numele: " + employee.getName() + "\n");
        }

        return result.toString();
    }

    public Map<Employee, Map<String, Integer>> calculateNrOfTask(Map<Employee, List<Task>> map){
        Map<Employee, Map<String, Integer>> myInnerMap = new HashMap<>();

        for(Employee employee: map.keySet()){

            List<Task> tasks = map.get(employee);
            if(tasks == null){
                continue;
            }

            int completed = 0;
            int uncompleted = 0;

            for(Task t: tasks){
                if(t.getStatusTask().equals("Completed")){
                    completed++;
                } else if (t.getStatusTask().equals("Uncompleted")){
                    uncompleted++;
                }
            }

            Map<String, Integer> taskCount = new HashMap<>();
            taskCount.put("Completed", completed);
            taskCount.put("Uncompleted", uncompleted);

            myInnerMap.put(employee, taskCount);
        }

        return myInnerMap;
    }

}