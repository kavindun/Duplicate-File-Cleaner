/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duplicatefilecleaner;
import java.awt.List;
import java.io.File;
import java.util.Scanner;
import java.util.Stack;


/**
 *
 * @author ABS
 */
public class DuplicateFileCleaner {

    /**
     * @param args the command line arguments
     */
    static Stack<String> pathStack=new Stack<>(); //this stack is used to stroe all paths of excutable application.
   
    /*
    *find paths of all excutable application
    
    */
    
    public static void filesPath(String folderPath){
        File currentFile=new File(folderPath);
        String[] filesNameArr=currentFile.list();
        for(int a=0;a<filesNameArr.length;a++){
            String folderPath1=folderPath+"\\"+filesNameArr[a];
            File checkPath=new File(folderPath1);
            if(checkPath.isFile()){
                pathStack.push(folderPath1);
            }
            else{
            filesPath(folderPath1);
            }   
        }
    }
   
    /*
    * delete all duplicate excutable applications.
    * this fuction work with command line interface.
    * in this fuction ,programe take user input as command line.
    */
    
    public static void deleteDuplicateFile(){
        int stage=0;
        int stackSize=pathStack.size();
        String[] pathsArry=new String[stackSize];
        for(int b=0;b<stackSize;b++){
            if(!pathStack.empty()){
            pathsArry[b]=pathStack.pop();
            }
            else{
                System.out.println("Stack is empty");
            }
        }
        for(int i=0;i<pathsArry.length;i++){
            if(pathsArry[i]==null){
            System.out.println("path "+pathsArry[i]);
            }
        }
        for(int i=0;i<pathsArry.length;i++){
            String[] duplicates=null;   
            if(!(pathsArry[i]=="delete")){
                 duplicates=searchDuplicats(pathsArry, i);
            if(!(duplicates.length==1)){
                stage+=1;
            System.out.println("Duplicate Files >>");
            System.out.println("Stage - "+stage);
            for(int j=0;j<duplicates.length;j++){
                System.out.println(" "+j+" - "+duplicates[j]);
            }
                Scanner input=new Scanner(System.in);
                System.out.print("Do you want Skipe this stage (yes/no) >> ");
                String stageSkip=input.nextLine();
                System.out.println("");
                if(!stageSkip.equals("yes")){
                    System.out.print("How does delete duplicate files(auto/manual) ? >> ");
                    String method=input.nextLine();
                    if(method.equals("auto")){
                        for(int au=1;au<duplicates.length;au++){
                            File auDelete=new File(duplicates[au]);
                            boolean isDelete=auDelete.delete();
                            if(isDelete==true){
                                System.out.println("Delete File "+duplicates[au]);
                            }
                        }
                    }
                    else{
                        for(int manualSelect=0;manualSelect<duplicates.length;manualSelect++){
                            System.out.println(manualSelect+" --> "+duplicates[manualSelect]);
                        }
                        System.out.print("Enter number of delete files (>> 0 2 3 ) >>");
                        String[] numberArrStr=input.nextLine().split(" ");
                        int[] numberArrInt=new int[numberArrStr.length];
                        for(int strConvertInt=0;strConvertInt<numberArrStr.length;strConvertInt++){
                            numberArrInt[strConvertInt]=Integer.parseInt(numberArrStr[strConvertInt]);
                        }
                        for(int manual=0;manual<numberArrInt.length;manual++){
                            File auDelete=new File(duplicates[numberArrInt[manual]]);
                            boolean isDelete=auDelete.delete();
                            if(isDelete==true){
                                System.out.println("Delete File "+duplicates[numberArrInt[manual]]);
                            }
                    }
                }
            }
            }
        }
    }
    }
    
    /*
    * this function is used to search duplicate file.
    * input parameters are pathsArry and selectionIndex.
    * pathsArry is String array and it has all paths of excutable application.
    * selectionIndex is integer and it is a selected index value of pathsArry. 
    * it is return string array which have stored paths relatede to duplicate excutable applications of selected index(selected path of application).
    */
    
    public static String[] searchDuplicats(String[] pathsArry,int selectionIndex){
        String[] duplicates;
        Stack<String> tempStack=new Stack<>();
        File comperFile=new File(pathsArry[selectionIndex]);
        for(int a=0;a<pathsArry.length;a++){
            if(!pathsArry[a].equals("delete")){
            File checkingFile=new File(pathsArry[a]);       
            if(comperFile.length()==checkingFile.length()){
               if(fileNameCompere(comperFile, checkingFile)){
                   tempStack.push(pathsArry[a]);
                   pathsArry[a]="delete";
               }
            }
            }
        }
        duplicates=new String[tempStack.size()];
        for(int b=0;b<duplicates.length;b++){
            duplicates[b]=tempStack.pop();
        }
        return duplicates;
    }
    
    /*
    * main function.
    */
    
    public static void main(String[] args){
        wellcome();
        System.out.println("");
        filesPath("E:\\s");
        deleteDuplicateFile();
        System.out.println("End....");
    }
    
    /*
    * Begining of program.
    */
    
    public static void wellcome(){
        for(int a=0;a<20;a++){
            for(int b=0;b<6;b++){
                System.out.print("K");
            }
            if(a<9){
            for(int c=0;c<24-a*3;c++){
                System.out.print(" ");
            }
            }
            if(a>11){
                for(int d=0;d<3*(a-11);d++){
                    System.out.print(" ");
                }
            }
            for(int b=0;b<6;b++){
                System.out.print("K");
            }
            System.out.println("");
        }
    }
    
   /*
    * excutable file names are compering funtion.
    */
   
    public static boolean fileNameCompere(File comperFile,File checkingFile){
                if(comperFile.getName().equals(checkingFile.getName())){               
                    return true;
                }
                else{
                    return false;
                }
    }
    
}
