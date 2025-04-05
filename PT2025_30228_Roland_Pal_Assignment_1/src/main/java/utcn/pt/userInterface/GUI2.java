package utcn.pt.userInterface;

import utcn.pt.businessLogic.TaskManagement;
import utcn.pt.businessLogic.Utility;
import utcn.pt.dataAccess.Deserializer;
import utcn.pt.dataAccess.Serializer;
import utcn.pt.dataModel.ComplexTask;
import utcn.pt.dataModel.Employee;
import utcn.pt.dataModel.SimpleTask;
import utcn.pt.dataModel.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GUI2 {

    private Map<Employee, List<Task>> map = new HashMap<>();
    private ArrayList<Task> taskList = new ArrayList<>();
    private TaskManagement manager;
    private Utility util;

    public GUI2() {
        manager = new TaskManagement(map);
        manager.loadData("employee.dat");
        util = new Utility(manager);
        manager.showEmployeeForTesting();
        taskList = loadDataList("tasks.dat");
        showTaskListForTesting();
        manager.showEmployeesTasks();
        createGUI();
    }

    public static void main(String[] argv) {
        GUI2 startGUI = new GUI2();
    }

    private static JButton buttonStyle(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.BLACK);
        button.setBackground(new Color(208, 233, 255));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(187, 218, 245));
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(208, 233, 255)); // Revine la culoarea initiala
            }
        });

        return button;
    }

    private void createGUI() {
        JFrame frame =  new JFrame("TaskManager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);


        JPanel principalPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        principalPanel.setBackground(new Color(208, 208, 208));

        JButton AddEmployeeBTN = new JButton("Add Employee");
        JButton AddTaskBTN = new JButton("Add Task");
        JButton AssignTaskToEmployeeBTN = new JButton("Assign task to an employee");
        JButton viewEmployeeANDTasks = new JButton("View employee and their tasks");
        JButton modifyStatus = new JButton("Modify the status of a task");
        JButton viewUtilityStatictics = new JButton("View utility statistics");

        // Button design
        AddEmployeeBTN = buttonStyle(AddEmployeeBTN);
        AddTaskBTN = buttonStyle(AddTaskBTN);
        AssignTaskToEmployeeBTN = buttonStyle(AssignTaskToEmployeeBTN);
        viewEmployeeANDTasks = buttonStyle(viewEmployeeANDTasks);
        modifyStatus = buttonStyle(modifyStatus);
        viewUtilityStatictics = buttonStyle(viewUtilityStatictics);

        principalPanel.add(AddEmployeeBTN);
        principalPanel.add(AddTaskBTN);
        principalPanel.add(AssignTaskToEmployeeBTN);
        principalPanel.add(viewEmployeeANDTasks);
        principalPanel.add(modifyStatus);
        principalPanel.add(viewUtilityStatictics);

        frame.add(principalPanel);

        // -------------------------- ADD EMPLOYEE -----------------------

        AddEmployeeBTN.addActionListener( e -> {
            JFrame addEmployeeFrame =  new JFrame("Add employee");
            addEmployeeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            addEmployeeFrame.setSize(300, 300);

            JPanel panelAddEmployee = new JPanel();
            panelAddEmployee.setLayout(new GridLayout(2, 2));

            JLabel labelEmployeeID = new JLabel("Employee ID: ");
            JTextField textFieldEmployeeID = new JTextField();
            JLabel labelEmployeeName = new JLabel("Employee Name: ");
            JTextField textFieldEmployeeName = new JTextField();

            panelAddEmployee.add(labelEmployeeID);
            panelAddEmployee.add(textFieldEmployeeID);
            panelAddEmployee.add(labelEmployeeName);
            panelAddEmployee.add(textFieldEmployeeName);


            JButton buttonAddEmployee = new JButton("Add Employee");

            buttonAddEmployee.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int id = -1;

                    if(!textFieldEmployeeID.getText().isEmpty())
                        id = Integer.parseInt(textFieldEmployeeID.getText());

                    String name = textFieldEmployeeName.getText();

                    if(id <= -1 || name.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "ID '>= 0' and NAME are required!", "Error", JOptionPane.ERROR_MESSAGE);
                    }else {
                        Employee newEmp = new Employee(id, name);
                        manager.addEmployee(newEmp);
                        manager.saveData("employee.dat");

                        JOptionPane.showMessageDialog(null, "Employee " + name + " was added!");
                    }

                }
            });

            JPanel panelForBTN = new JPanel();
            panelForBTN.setLayout(new GridLayout(1,1));

            panelForBTN.add(buttonAddEmployee);

            addEmployeeFrame.add(panelAddEmployee, BorderLayout.NORTH);
            addEmployeeFrame.add(panelForBTN, BorderLayout.SOUTH);
            addEmployeeFrame.setVisible(true);
        });


        // -------------------------- ADD TASK ----------------------------------

        AddTaskBTN.addActionListener( e -> {
            JFrame addTaskFrame =  new JFrame("Add task");
            addTaskFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            addTaskFrame.setSize(300, 300);

            JPanel panelAddTask = new JPanel();
            panelAddTask.setLayout(new GridLayout(6, 2));

            JLabel labelTaskType = new JLabel("Task Type: ");
            JComboBox<String> comboBoxForTask = new JComboBox<>(new String[] { "Select", "SimpleTask", "ComplexTask" });
            JLabel labelTaskID = new JLabel("Task ID: ");
            JTextField textFieldTaskID = new JTextField();

            panelAddTask.add(labelTaskType);
            panelAddTask.add(comboBoxForTask);
            panelAddTask.add(labelTaskID);
            panelAddTask.add(textFieldTaskID);

            JLabel labelStartH = new JLabel("Start Hour: ");
            JTextField textFieldStartH = new JTextField();
            JLabel labelEndH = new JLabel("End Hour: ");
            JTextField textFieldEndH = new JTextField();
            JLabel labelStatus = new JLabel("Task Status: ");
            JComboBox<String> comboBoxStatus = new JComboBox<>(new String[] { "Completed", "Uncompleted" });

            JLabel labelTaskList = new JLabel("Task list-ID(x,y): ");
            JTextField textFieldTaskList = new JTextField();

            // Initial setez toate astea pe HIDE
            panelAddTask.add(labelStartH);
            panelAddTask.add(textFieldStartH);
            panelAddTask.add(labelEndH);
            panelAddTask.add(textFieldEndH);
            panelAddTask.add(labelStatus);
            panelAddTask.add(comboBoxStatus);
            panelAddTask.add(labelTaskList);
            panelAddTask.add(textFieldTaskList);

            labelStartH.setVisible(false);
            textFieldStartH.setVisible(false);
            labelEndH.setVisible(false);
            textFieldEndH.setVisible(false);
            labelTaskList.setVisible(false);
            textFieldTaskList.setVisible(false);
            labelStatus.setVisible(false);
            comboBoxStatus.setVisible(false);

            comboBoxForTask.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String selectedTaskType = (String) comboBoxForTask.getSelectedItem();
                    if ("SimpleTask".equals(selectedTaskType)) {
                        labelStartH.setVisible(true);
                        textFieldStartH.setVisible(true);
                        labelEndH.setVisible(true);
                        textFieldEndH.setVisible(true);
                        labelStatus.setVisible(true);
                        comboBoxStatus.setVisible(true);

                        labelTaskList.setVisible(false);
                        textFieldTaskList.setVisible(false);
                    } else if("ComplexTask".equals(selectedTaskType)){
                        labelStartH.setVisible(false);
                        textFieldStartH.setVisible(false);
                        labelEndH.setVisible(false);
                        textFieldEndH.setVisible(false);

                        labelStatus.setVisible(true);
                        comboBoxStatus.setVisible(true);
                        labelTaskList.setVisible(true);
                        textFieldTaskList.setVisible(true);
                    } else {
                        labelStartH.setVisible(false);
                        textFieldStartH.setVisible(false);
                        labelEndH.setVisible(false);
                        textFieldEndH.setVisible(false);
                        labelTaskList.setVisible(false);
                        textFieldTaskList.setVisible(false);
                    }

                }
            });

            JButton buttonAddTask = new JButton("Add Task");

            buttonAddTask.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int taskID = -1;
                    int startHour = -1;
                    int endHour = -1;
                    String status = (String) comboBoxStatus.getSelectedItem();

                    if(!textFieldTaskID.getText().isEmpty())
                        taskID = Integer.parseInt(textFieldTaskID.getText());

                    if(!textFieldStartH.getText().isEmpty())
                        startHour = Integer.parseInt(textFieldStartH.getText());

                    if(!textFieldEndH.getText().isEmpty())
                        endHour = Integer.parseInt(textFieldEndH.getText());

                    String selectedTaskType = (String) comboBoxForTask.getSelectedItem();

                    if ("SimpleTask".equals(selectedTaskType)) {
                        if ((taskID <= -1 || startHour <= -1 || endHour <= -1))
                            JOptionPane.showMessageDialog(null, "TaskID/StartHour/EndHour '>=0' are required!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                        if ("SimpleTask".equals(selectedTaskType)) {
                            SimpleTask simpleT = new SimpleTask(taskID, status, startHour, endHour);
                            taskList.add(simpleT);
                            saveDataList(taskList, "tasks.dat");
                        } else {
                            String tasksID = textFieldTaskList.getText();

                            String[] values = tasksID.split(",");
                            ArrayList<Task> newTaskList = new ArrayList<>();
                            for(String value : values){
                                int integerValue = Integer.parseInt(value);
                                for(Task t : taskList){
                                    if(t.getIdTask() == integerValue){
                                        newTaskList.add(t);
                                        break;
                                    }
                                }
                            }

                            ComplexTask complexT = new ComplexTask(taskID, status, newTaskList);
                            taskList.add(complexT);
                            saveDataList(taskList, "tasks.dat");
                        }

                        JOptionPane.showMessageDialog(null, "Task was added succesfully!");
                    }

            });


            JPanel panelForBTN = new JPanel();
            panelForBTN.setLayout(new GridLayout(1,1));

            panelForBTN.add(buttonAddTask);

            addTaskFrame.add(panelAddTask, BorderLayout.NORTH);
            addTaskFrame.add(panelForBTN, BorderLayout.SOUTH);
            addTaskFrame.setVisible(true);

        });


        // -------------------------- Assign task to an employee --------------------------

        AssignTaskToEmployeeBTN.addActionListener( e -> {
            JFrame assigTaskToEmployeeFrame =  new JFrame("Assign task to an employee");
            assigTaskToEmployeeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            assigTaskToEmployeeFrame.setSize(300, 300);


            JPanel panelAsignTaskToEmployee = new JPanel();
            panelAsignTaskToEmployee.setLayout(new GridLayout(2, 2));

            JLabel labelSelectEmployeeID = new JLabel("Employee ID: ");
            JTextField textFieldSelectEmployeeID = new JTextField();

            JLabel labelSelectTaskID = new JLabel("Task ID: ");
            JTextField textFieldSelectTaskID = new JTextField();

            panelAsignTaskToEmployee.add(labelSelectEmployeeID);
            panelAsignTaskToEmployee.add(textFieldSelectEmployeeID);
            panelAsignTaskToEmployee.add(labelSelectTaskID);
            panelAsignTaskToEmployee.add(textFieldSelectTaskID);

            JButton buttonAsignTaskToEmployee = new JButton("Asign task to employee");

            buttonAsignTaskToEmployee.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int empID = -1;
                    int taskID = -1;

                    if(!textFieldSelectEmployeeID.getText().isEmpty()) {
                        empID = Integer.parseInt(textFieldSelectEmployeeID.getText());
                    }

                    if(!textFieldSelectTaskID.getText().isEmpty()) {
                        taskID = Integer.parseInt(textFieldSelectTaskID.getText());
                    }

                    if(empID <= -1 || taskID <= -1){
                        JOptionPane.showMessageDialog(null, "TaskID/EmployeeID '>=0' are required!", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        Employee newEmployee = null;
                        for(Employee employeeList: manager.getMap().keySet()){
                            if(employeeList.getIdEmployee() == empID){
                                newEmployee = employeeList;
                                break;
                            }
                        }

                        if(newEmployee == null){
                            JOptionPane.showMessageDialog(null, "Employee ID doesn't exists!", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        Task newTask = null;
                        for(Task t: taskList){
                            if(t.getIdTask() == taskID){
                                newTask = t;
                                break;
                            }
                        }

                        if(newTask == null){
                            JOptionPane.showMessageDialog(null, "Task ID doesn't exists!", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        manager.assignTaskToEmployee(empID, newTask);
                        JOptionPane.showMessageDialog(null, "Task was assigned succesfull!");

                    }


                }
            });

            JPanel panelForBTN = new JPanel();
            panelForBTN.setLayout(new GridLayout(1,1));

            panelForBTN.add(buttonAsignTaskToEmployee);


            assigTaskToEmployeeFrame.add(panelAsignTaskToEmployee, BorderLayout.NORTH);
            assigTaskToEmployeeFrame.add(panelForBTN, BorderLayout.SOUTH);
            assigTaskToEmployeeFrame.setVisible(true);
        });


        // --------------------------- View employee and their tasks ----------------------

        viewEmployeeANDTasks.addActionListener( e -> {
            JFrame viewEmpFrame =  new JFrame("Add employee");
            viewEmpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            viewEmpFrame.setSize(300, 300);

            JPanel panelForViewEmp = new JPanel();
            panelForViewEmp.setLayout(new GridLayout(1, 2));

            JLabel labelEmpID = new JLabel("ID employee: ");
            JTextField textFieldEmpID = new JTextField();


            JTextArea textAreaTasks = new JTextArea();
            JScrollPane scrollPane = new JScrollPane(textAreaTasks);


            panelForViewEmp.add(labelEmpID);
            panelForViewEmp.add(textFieldEmpID);

            JButton btn = new JButton("Show");

            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int empID = -1;

                    if(!textFieldEmpID.getText().isEmpty()){
                        empID = Integer.parseInt(textFieldEmpID.getText());
                    }

                    if(empID == -1) {
                        JOptionPane.showMessageDialog(null, "EmployeeID '>=0' are required!", "Error", JOptionPane.ERROR_MESSAGE);
                    } else{
                        textAreaTasks.setText("");

                        Employee ourEmp = null;
                        for(Employee em : manager.getMap().keySet()) {
                            if(em.getIdEmployee() == empID) {
                                ourEmp = em;
                                break;
                            }
                        }
                        if(ourEmp == null){
                            JOptionPane.showMessageDialog(null, "Employee doesn't exist!", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        textAreaTasks.append("Employee " + ourEmp.getName() + " work duration is "
                                + manager.calculateEmployeeWorkDuration(empID) + "hours for task ID's:\n");


                        if(manager.getMap().get(ourEmp) == null){
                            textAreaTasks.append("No avaible tasks!");
                            return;
                        }

                        for(Task t : manager.getMap().get(ourEmp)){
                            textAreaTasks.append( t.getIdTask() + "\n");
                        }
                        textAreaTasks.append("\n");

                    }
                }
            });


            viewEmpFrame.add(panelForViewEmp, BorderLayout.NORTH);
            viewEmpFrame.add(scrollPane, BorderLayout.CENTER);
            viewEmpFrame.add(btn, BorderLayout.SOUTH);
            viewEmpFrame.setVisible(true);


        });



        // ---------------------------- Modify status of a task to an employee ---------------------

        modifyStatus.addActionListener( e -> {
            JFrame modifyStatusFrame =  new JFrame("Modify status");
            modifyStatusFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            modifyStatusFrame.setSize(300, 300);

            JPanel panelmodifyStatus = new JPanel();
            panelmodifyStatus.setLayout(new GridLayout(3, 2));

            JLabel labelEmployeeID = new JLabel("Employee ID: ");
            JTextField textFieldEmployeeID = new JTextField();
            JLabel labelTaskID = new JLabel("Task ID: ");
            JTextField textFieldTaskID = new JTextField();

            panelmodifyStatus.add(labelEmployeeID);
            panelmodifyStatus.add(textFieldEmployeeID);
            panelmodifyStatus.add(labelTaskID);
            panelmodifyStatus.add(textFieldTaskID);


            JButton buttonModifyStatus = new JButton("Modify status");

            buttonModifyStatus.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int empID = -1;
                    int taskID = -1;

                    if(!textFieldEmployeeID.getText().isEmpty())
                        empID = Integer.parseInt(textFieldEmployeeID.getText());

                    if(!textFieldTaskID.getText().isEmpty())
                        taskID = Integer.parseInt(textFieldTaskID.getText());

                    if(empID == -1 || taskID == -1){
                        JOptionPane.showMessageDialog(null, "ID '>= 0' are required!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    int x = manager.modifyTaskStatus(empID, taskID);
                    if(x != -1)
                        JOptionPane.showMessageDialog(null, "Status was modified successfully.");
                 }
            });


            modifyStatusFrame.add(panelmodifyStatus);
            modifyStatusFrame.add(buttonModifyStatus, BorderLayout.SOUTH);
            modifyStatusFrame.setVisible(true);
        });


        // ----------------------------- Utility statistics --------------------------------

        viewUtilityStatictics.addActionListener( e -> {
            JFrame utilityFrame =  new JFrame("Modify status");
            utilityFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            utilityFrame.setSize(400, 400);

            JPanel utilityPanel= new JPanel();
            utilityPanel.setLayout(new BorderLayout());

            JTextArea textArea= new JTextArea();
            JScrollPane scrollPane = new JScrollPane(textArea);


            textArea.setText("");
            String outputString = util.filterEmployees(manager.getMap());
            textArea.append(outputString);


            Map<Employee, Map<String, Integer>> taskCountMap = util.calculateNrOfTask(manager.getMap());
            textArea.append("\nTask count for each employee:\n");

            for (Map.Entry<Employee, Map<String, Integer>> entry : taskCountMap.entrySet()) {
                Employee employee = entry.getKey();
                Map<String, Integer> taskCount = entry.getValue();
                textArea.append("Employee: " + employee.getName() + "\n");
                textArea.append(" - Completed: " + taskCount.get("Completed") + "\n");
                textArea.append(" - Uncompleted: " + taskCount.get("Uncompleted") + "\n");
                textArea.append("\n");
            }

            utilityPanel.add(scrollPane);

            utilityFrame.add(utilityPanel);
            utilityFrame.setVisible(true);


        });


        manager.saveData("employee.dat");
        saveDataList(taskList, "tasks.dat");
        frame.setVisible(true);
    }

    private void saveDataList(ArrayList<Task> data, String filename) {
        Serializer.saveDateList(data, filename);
    }

    private ArrayList<Task> loadDataList(String filename) {
        return Deserializer.loadDateList(filename);
    }

    private void showTaskListForTesting(){
        for(Task t : taskList){
            System.out.println("Task ID " + t.getIdTask());
        }
    }

}
