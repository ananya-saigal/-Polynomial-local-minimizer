
public class Statistics {
    //The class only create statistics methods that will be used in the SteepestDescent class

    //For average, we want to find the average of  array passed in for each data type(int, double, long)
    public static double average_double(double[] double_array) {
        double sum = 0.0;
        for (int i = 0; i < double_array.length; i++) {
            sum += double_array[i];
        }
        return (sum/double_array.length);
    }
    public static double average_int(int[] int_array) {
        double sum = 0;
        for (int i = 0; i < int_array.length; i++) {
            sum += int_array[i];
        }
        return (sum/int_array.length);
    }
    public static double average_long(long[] long_array) {
        double sum = 0;
        for (int i = 0; i < long_array.length; i++) {
            sum += long_array[i];
        }
        return sum / long_array.length;
    }

    //For standard deviation, we want to find the standard deviation of  array passed in for each data type(int, double, long)
    public static double std_double(double[] double_array) {
        double sum = 0.0;
        double mean = average_double(double_array);
        for (int i = 0; i < double_array.length; i++) {
            sum += Math.pow((double_array[i] - mean), 2);
        }
        return Math.sqrt(sum / (double_array.length - 1));
    }
    public static double std_int(int[] int_array) {
        double sum = 0.0;
        double mean = average_int(int_array);
        for (int i = 0; i < int_array.length; i++) {
            sum += Math.pow((int_array[i] - mean), 2);
        }
        return Math.sqrt(sum / (int_array.length - 1));
    }
    public static double std_long(long[] long_array) {
        double sum = 0.0;
        double mean = average_long(long_array);
        for (int i = 0; i < long_array.length; i++) {
            sum += Math.pow((long_array[i] - mean), 2);
        }
        return Math.sqrt(sum / (long_array.length - 1));
    }

    //for min, we want to find the min of  array passed in for each data type(int, double, long)
    public static double min_double(double[] double_array) {
       //innitialize the min to as the first element in the array
        double min = double_array[0];
        for (int i = 0; i < double_array.length; i++) {
            if (double_array[i] < min) {
                min = double_array[i];
            }
        }
        return min;
    }
    public static int min_int(int[] int_array) {
        int min = int_array[0];
        for (int i = 0; i < int_array.length; i++) {
            if (int_array[i] < min) {
                min = int_array[i];
            }
        }
        return min;
    }
    public static long min_long(long[] long_array) {
        long min = long_array[0];
        for (int i = 0; i < long_array.length; i++) {
            if (long_array[i] < min) {
                min = long_array[i];
            }
        }
        return min;
    }

    // simmilar for max, we want to find the max of  array passed in for each data type(int, double, long)
    //the initial value of max is the first element in the array
    public static double max_double(double[] double_array) {
        double max = double_array[0];
        for (int i = 0; i < double_array.length; i++) {
            if (double_array[i] > max) {
                max = double_array[i];
            }
        }
        return max;
    }
    public static int max_int(int[] int_array) {
        int max = int_array[0];
        for (int i = 0; i < int_array.length; i++) {
            if (int_array[i] > max) {
                max = int_array[i];
            }
        }
        return max;
    }
    public static long max_long(long[] long_array) {
        long max = long_array[0];
        for (int i = 0; i < long_array.length; i++) {
            if (long_array[i] > max) {
                max = long_array[i];
            }
        }
        return max;
    }
}

