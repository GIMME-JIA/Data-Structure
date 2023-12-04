package 基础.二分查找;

public class BinarySearch {
    
    
    
    public static int binarySearchBasic(int[] arr,int target){
        
        int i = 0,j = arr.length -1;
        while(i <= j){
            int m = i + j / 2;
            if(target == arr[m]){
                return m;
            } else if (target > arr[m]) {
                i = m + 1;
            } else if (target < arr[m]) {
                j = m - 1;
            }
        }
        return -1;
    }
    
    
}
