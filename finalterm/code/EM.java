class EM {
    static final int data[] = { 8, 3, 6, 9, 7, 6, 2, 6, 4, 4, 5, 7 };
    static Boal boal[];
    static final String moji[] = {"A", "B", "C"};

    static class Boal {
        double plist[];
        double plist2[];
        int num;
        double mu;
        double sigma;
        double p;

        public Boal(int num, double mu, double sigma, double p) {
            this.num = num;
            plist = new double[num];
            plist2 = new double[num];
            this.mu = mu;
            this.sigma = sigma;
            this.p = p;
        }
    }

    public static double norm(double x, Boal boal) {
        double mu = boal.mu;
        double sigma = boal.sigma;
        double ans = 1.0 / Math.sqrt(2 * Math.PI * sigma);
        ans *= Math.exp(((mu - x) * (x - mu)) / (2 * sigma));
        return ans;
    }

    public static double expect() {
        int n = data.length;
        double ans = 1.0;
        for (int i = 0; i < n; ++i) {
            double tmp = 0.0;
            for (int j = 0; j < 3; ++j) {
                tmp += boal[j].p * boal[j].plist[i];
            }
            ans *= tmp;
        }
        return ans;
    }

    public static double update() {
        // calc P(x|A)
        int n = data.length;
        for (int i = 0; i < n; ++i) {
            double x = (double) data[i];
            for (int j = 0; j < 3; ++j) {
                boal[j].plist[i] = norm(x, boal[j]);
            }
        }

        // calc P(A|x)
        double sum = 0.0;
        for (int i = 0; i < n; ++i) {
            sum = 0.0;
            for (int j = 0; j < 3; ++j) {
                sum += boal[j].plist[i] * boal[j].p;
            }
            for (int j = 0; j < 3; ++j) {
                boal[j].plist2[i] = boal[j].plist[i] * boal[j].p / sum;
            }
        }

        // for (int i = 0; i < 3; ++i) {
        //     System.out.println(moji[i]);
        //     for (double p : boal[i].plist2)
        //         System.out.print(p + " ");
        //     System.out.println();
        // }
        
        double sum2 = 0.0;
        sum = 0.0;
        for (int i = 0; i < 3; ++i) {
            sum = 0.0;
            // calc P(A)
            for (int j = 0; j < n; ++j) {
                sum += boal[i].plist2[j];
            }
            boal[i].p = sum / n;
            // calc mu
            sum2 = 0.0;
            for (int j = 0; j < n; ++j) {
                sum2 += (double) data[j] * boal[i].plist2[j];
            }
            boal[i].mu = sum2 / sum;
            double mu = boal[i].mu;
            // calc sigma
            sum2 = 0.0;
            for (int j = 0; j < n; ++j) {
                double x = (double) data[j] - mu;
                x = x * x;
                sum2 += boal[i].plist2[j] * x;
            }
            boal[i].sigma = sum2 / sum;
        }
        // System.out.println("         P()               mu                   sigma");
        // for (int i = 0; i < 3; ++i) {
        //     System.out.println(moji[i] + ":" + boal[i].p + "|" + boal[i].mu + "|" + boal[i].sigma);
        // }
        return expect();
    }

    public static void main(String args[]) {
        boal = new Boal[3];
        double p = 1.0 / 3.0;
        boal[0] = new Boal(12, 8.0, 1.0, p);
        boal[1] = new Boal(12, 3.0, 1.0, p);
        boal[2] = new Boal(12, 6.0, 1.0, p);
        double l = 0.0;
        double d = 5.0E-14;
        for (int i = 0; i < 1000; ++i) {
            double e = update();
            if (Math.abs(e - l) < d)
            break;
            l = e;
        }
        
        System.out.println("         P()                     mu                 sigma");
        for (int i = 0; i < 3; ++i) {
            System.out.println(moji[i] + " : " + boal[i].p + " | " + boal[i].mu + " | " + boal[i].sigma);
        }
        System.out.println("尤度 : " + l);
    }
}