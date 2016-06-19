import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    private static final String ROOT_PATH = "/Users/zhuwenbo/IdeaProjects/ProguardDictionaryGenerator/src/";

    private static BufferedImage image;
    private static Graphics graphics;
    private static int width, height;
    private static int baseline;

    public static void main(String[] args) throws Exception {
        preparePaint();
        int start = 0x0100;
        int end = 0x1100;
        List<Holder> list = new ArrayList<>(end - start);
        for (int i = start; i < end; i++) {
            char c = (char) i;
            if (c < start || c >= end) continue;
            String s = String.valueOf(c);
            list.add(new Holder(s, getSizeForString(s)));
        }
        Collections.sort(list);
        File file = new File(ROOT_PATH, "dict.txt");
        FileOutputStream fos = new FileOutputStream(file);
        for (Holder holder : list) {
            fos.write(holder.character.getBytes("utf-8"));
//            fos.write(' ');
//            fos.write(String.valueOf(holder.complexity).getBytes("utf-8"));
            fos.write('\n');
        }
        fos.flush();
        fos.close();
//        fos = new FileOutputStream(new File(ROOT_PATH, "abc.png"));
//        graphics.drawString("龘", 0, baseline);
//        ImageIO.write(image, "png", fos);
//        fos.flush();
//        fos.close();
    }

    private static void getFonts() {
        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fonts = environment.getAvailableFontFamilyNames();
        for (String font1 : fonts) {
            System.out.println(font1);
        }
    }

    private static void preparePaint() throws Exception {
        Font font = new Font("PingFang HK", Font.PLAIN, 200);

        image = new BufferedImage(300, 300, BufferedImage.TYPE_INT_RGB);//test
        graphics = image.getGraphics();
        graphics.setFont(font);
        FontMetrics fontMetrics = graphics.getFontMetrics();

        width = fontMetrics.stringWidth("四");
        height = fontMetrics.getHeight();
        baseline = fontMetrics.getAscent();

        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        graphics = image.getGraphics();
        graphics.setFont(font);

        System.out.println("width=" + width);
        System.out.println("height=" + height);
    }

    private static int getSizeForString(String str) throws Exception {
        graphics.clearRect(0, 0, width, height);
        graphics.setColor(Color.white);
        graphics.fillRect(0, 0, width, height);
        graphics.setColor(Color.black);
        graphics.drawString(str, 0, baseline);
        //计算汉字所有像素的极惯性矩,用于比较
        int sum_x_2 = 0, sum_x = 0;
        int sum_y_2 = 0, sum_y = 0;
        int count = 0;
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                if (isBlack(image.getRGB(w, h))) {
                    sum_x += w;
                    sum_x_2 += w * w;
                    sum_y += h;
                    sum_y_2 += h * h;
                    count++;
                }
            }
        }
        if (count == 0) return 0;
        return sum_x_2 - sum_x * sum_x / count + sum_y_2 - sum_y * sum_y / count;
    }

    private static boolean isBlack(int color) {
        Color c = new Color(color);
        int r = c.getRed();
        int g = c.getGreen();
        int b = c.getBlue();
        return r * r + g * g + b * b < 127 * 127 * 3;
    }

    private static class Holder implements Comparable<Holder> {
        private String character;
        private int complexity;

        Holder(String character, int complexity) {
            this.character = character;
            this.complexity = complexity;
        }

        @Override
        public int compareTo(Holder o) {
            return complexity - o.complexity;
        }

        @Override
        public String toString() {
            return character + " " + complexity;
        }
    }
}
