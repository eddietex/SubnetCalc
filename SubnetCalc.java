import java.util.Scanner;

/**
 *
 * @author esdrasevt
 */
public class SubnetCalc {

    int prefix;


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        boolean finish = false;
        char c;

        SubnetCalc brain = new SubnetCalc();
        Scanner scanner = new Scanner(System.in);

        while(!finish){
            System.out.print(
                "Enter the slash notation (e.g. 1.1.1.1/23) of your network: ");
            String ip = scanner.nextLine();
            try{
                byte[] ipBinArr = brain.ipStrToBinArr(ip);
                int prefix = brain.prefix;
                String numIps = String.valueOf(brain.networkTotalIps(prefix));
                String firstIp = String.valueOf(brain.firstIpAddress(ipBinArr, prefix));
                String lastIp = String.valueOf(brain.lastIpAddress(ipBinArr, prefix));
                String subnet = String.valueOf(brain.subNetMask(prefix));

                System.out.println("============================\n"
                        + "           RESULT          \n"
                        + "============================");
                System.out.println("Num of accesses: " + numIps);
                System.out.println("Beginning IP(Network ID): " + firstIp);
                System.out.println("Ending IP(Broadcast): " + lastIp);
                System.out.println("Subnet Mask: " + subnet + "\n");

                System.out.print("Calculate again? y for YES or n for NO: ");
                c = scanner.next().charAt(0);
                scanner.nextLine();

                switch(c){
                    case 'n':
                        finish = true;
                        break;
                    case 'N':
                         finish = true;
                        break;
                    case 'y':
                        break;
                    case 'Y':
                        break;
                    default:
                         finish = true;
                }
        }
        catch (Exception e){
            System.out.println("=========================================================\n"
                        + "|| Error: Please use slash notation:e.g 192.168.0.1/23 ||\n"
                        + "=========================================================");
        }


        }

    }

    String firstIpAddress(byte[] ip, int prefix){
        String res = "";
        int count = 0;
        byte[] arr = new byte[32];
        String temp = "";

        for(int i=0; i<prefix; i++){
            arr[i] = ip[i];
        }
        for(int i=prefix; i<32; i++){
            arr[i] = 0;
        }
        for(int i=0;i<4;i++){
            for(int j=0;j<8;j++){
                temp+=arr[count];
                count++;
            }
            int tempIp = Integer.parseInt(temp, 2);
            temp = "";
            res+=tempIp;
            if(i < 3)res+=".";
        }
        return res;
    }

    public String lastIpAddress(byte[] ip, int prefix){
        String res = "";
        int count = 0;
        byte[] arr = new byte[32];
        String temp = "";

        for(int i=0; i<prefix; i++){
            arr[i] = ip[i];
        }
        for(int i=prefix; i<32; i++){
            arr[i] = 1;
        }
        for(int i=0;i<4;i++){
            for(int j=0;j<8;j++){
                temp+=arr[count];
                count++;
            }
            int tempIp = Integer.parseInt(temp, 2);
            temp = "";
            res+=tempIp;
            if(i < 3)res+=".";
        }
        return res;
    }

    //TODO: Implement this correctly
    public String subNetMask(int prefix){
        String res = "";
        int count = 0;
        byte[] arr = new byte[32];
        String temp = "";

        for(int i=0; i<prefix; i++){
            arr[i] = 1;
        }
        for(int i=prefix; i<32; i++){
            arr[i] = 0;
        }
        for(int i=0;i<4;i++){
            for(int j=0;j<8;j++){
                temp+=arr[count];
                count++;
            }
            int tempIp = Integer.parseInt(temp, 2);
            temp = "";
            res+=tempIp;
            if(i < 3)res+=".";
        }
        return res;
    }

    public int networkTotalIps(int prefix){
        int res;
        double temp;
        temp = Math.pow(2, 32-prefix);
        res = (int) temp;
        return res;
    }

    public byte[] ipStrToBinArr(String ip){
        String[] ipArr;
        String[] temp;
        String[] ipBinStr = new String[4];
        byte[] ipBinArr = new byte[32];
        int count = 0;

        ipArr = ip.split("\\.");
        temp = ipArr[3].split("\\/");
        ipArr[3] = temp[0];
        this.prefix = Integer.valueOf(temp[1]);

        for(int i = 0;i<ipArr.length;i++){
            int x = Integer.valueOf(ipArr[i]);
            String s = Integer.toBinaryString(0x100 | x).substring(1);
            ipBinStr[i] = s;
            for(int j = 0;j<s.length();j++){
                char c = s.charAt(j);
                ipBinArr[count] = Byte.valueOf(String.valueOf(c));
                count++;
            }
        }

        return ipBinArr;
    }

}
