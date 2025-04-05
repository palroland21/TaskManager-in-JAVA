package utcn.pt.businessLogic;

import utcn.pt.dataAccess.Deserializer;
import utcn.pt.dataAccess.Serializer;
import utcn.pt.dataModel.Employee;
import utcn.pt.dataModel.SimpleTask;
import utcn.pt.dataModel.Task;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class TaskManagement {
    private Map<Employee, List<Task>> map;

    public TaskManagement(Map<Employee, List<Task>> map) {
        this.map = map;
    }

    private Employee findMyEmployee(int idEmployee){
        Employee myEmp = null;
        for(Employee emp: map.keySet()){
            if(emp.getIdEmployee() == idEmployee) {
                myEmp = emp;
                break;
            }
        }

        if(myEmp == null)
            return null;
        else
            return myEmp;
    }

    public void assignTaskToEmployee(int idEmployee, Task task) {
        Employee myEmp = findMyEmployee(idEmployee);
        if(myEmp == null){
            System.out.println("Employee doesn't exist!");
            return;
        }

        List<Task> taskList = map.get(myEmp);
        if(taskList == null){
            System.out.println("Angajatul nu are taskuri!");
            taskList = new ArrayList<>();
        }

        taskList.add(task);

        map.put(myEmp, taskList);

        showEmployeesTasks();
        saveData("employee.dat");
    }

    public int calculateEmployeeWorkDuration(int idEmployee){
        int workDuration = 0;
        Employee myEmp = findMyEmployee(idEmployee);
        if(myEmp == null){
            return 0;
        }

        List<Task> taskList = map.get(myEmp);
        if(taskList == null){
            System.out.println("This employee doesn't have tasks!");
            return 0;
        }
        for(Task t : taskList){
            if(t.getStatusTask().equals("Completed")) {
                workDuration += t.estimatedDuration();
            }
        }

        return workDuration;
    }

    public int modifyTaskStatus(int idEmployee, int idTask){
        Employee myEmp = findMyEmployee(idEmployee);
        if(myEmp == null){
            JOptionPane.showMessageDialog(null, "Employee doesn't exist!", "Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        }

        List<Task> taskList = map.get(myEmp);
        if(taskList == null){
            JOptionPane.showMessageDialog(null, "Employee doesn't have tasks!", "Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
        int found = 0;
        for(Task t : taskList) {
            if (t.getIdTask() == idTask) {
                found = 1;
                if (t.getStatusTask().equals("Completed")) {
                    t.setStatusTask("Uncompleted");
                } else {
                    t.setStatusTask("Completed");
                }
            }
        }

        if(found == 0){
            JOptionPane.showMessageDialog(null, "Employee doesn't have the task ID specified!", "Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
        return 0;
    }

    public void addEmployee(Employee newEmployee){
        this.map.put(newEmployee, null);
    }

    public void showEmployeeForTesting() {
        for (Employee employee : map.keySet()) {
            System.out.println("Employee's " + employee.getName() + " with ID " + employee.getIdEmployee());
        }
    }

    public void showEmployeesTasks(){
        for(Employee employee: map.keySet()){
            System.out.print("Employee's " + employee.getName() + " with ID " + employee.getIdEmployee() + ", tasks ID: ");

            List<Task> taskList = map.get(employee);
            if(taskList == null) {
                System.out.println("No existing tasks!");
                continue;
            }

            for(Task t: taskList){
                System.out.print(t.getIdTask() + " ");
            }
            System.out.println();

        }
        System.out.println();
    }


    public void saveData(String filename){
        Serializer.saveDate(map, filename);
    }

    public void loadData(String filename){
        this.map = Deserializer.loadDate(filename);
    }

    public Map<Employee, List<Task>> getMap() {
        return map;
    }
}
