/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autotahvel;
import java.io.*;
import javax.swing.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.Optional;
import java.util.Scanner;
import org.openqa.selenium.JavascriptExecutor;
/** 
 * 
 * Данная программа автоматически запускает сайт tahvel.edu.ee, используя метод входа Smart-Id, вставляет личный код в поле, после отправляется код подтверждения на ваш телефон. 
 * Вам нужно будет только ввести код на телефоне. После входа программа автоматически перекинет вас во вкладку "Päevik".
 * Период ожидания во время подтверждения - 60 секунд. После этого программа приостановит работу (выкидывать из сайта не будет).
 * Если вам нужно перезаписать ваш isikukood - удалите файл isikukoodid.txt в корневой папке программы
 */
public class AutoTahvel {


  public static void main(String[] args) throws InterruptedException, IOException {
              System.setProperty("webdriver.chrome.driver","lib\\chromedriver.exe");
		WebDriver driver = new ChromeDriver ();
		driver.manage().window().maximize();
               
		driver.get("https://tahvel.edu.ee/#/");
		Thread.sleep(500);
                driver.findElement(By.xpath(".//button[@class='md-button md-ink-ripple']")).click();
                Thread.sleep(500);
                driver.findElement(By.xpath(".//button[@ng-click='showIndex(4)']")).click();
                Thread.sleep(500);
                driver.get("https://tahvel.edu.ee/hois_back/taraLogin");
                Thread.sleep(500);
                driver.findElement(By.xpath(".//a[@data-tab='smart-id']")).click();
                Thread.sleep(500);
                String filePath = "isikukoodid.txt";
                JFrame jFrame = new JFrame();
                FileWriter writer;
                writer = new FileWriter(filePath, true);
                FileReader fr = new FileReader(filePath);
                BufferedReader reader = new BufferedReader(fr);
                    if(reader.readLine()==null){
                        String getMessage = JOptionPane.showInputDialog(jFrame, "Введите ваш isikukood");
                        Encoder encoder = Base64.getEncoder(); 
                        String originalString = getMessage;
                        String encodedString = encoder.encodeToString(originalString.getBytes()); 
                            try {
                              writer.write(encodedString);
                              writer.close();
                                } catch (IOException e) {
                              e.printStackTrace();
                                }   
                    }
                FileReader fr2 = new FileReader(filePath);
                BufferedReader reader2 = new BufferedReader(fr2);
                Decoder decoder = Base64.getDecoder(); 
                String line = reader2.readLine();
                byte[] bytes = decoder.decode(line);
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("document.getElementById('sid-personal-code').type='password';");
                driver.findElement(By.id("sid-personal-code")).sendKeys(new String(bytes));
                
                Thread.sleep(500);
                driver.findElement(By.xpath(".//form[@id='smartIdForm']/table/tbody/tr[2]/td[2]/button")).click();
                WebElement myElement = (new WebDriverWait(driver, 60)).until(ExpectedConditions.presenceOfElementLocated(By.id("select_value_label_0")));
                Thread.sleep(500);
                driver.findElement(By.xpath(".//li[@class='parent-list-item'][7]/menu-toggle/button[@class='md-button-toggle md-button md-ink-ripple']/div[@class='layout-row flex']")).click();
                Thread.sleep(1000);

                if(!driver.findElements(By.xpath(".//menu-toggle/ul[@id='docs-menu-main.menu.myStudyInformation.label']/li[1]/menu-link/a[@class='md-button md-ink-ripple']")).isEmpty()){
                driver.findElement(By.xpath(".//menu-toggle/ul[@id='docs-menu-main.menu.myStudyInformation.label']/li[1]/menu-link/a[@class='md-button md-ink-ripple']")).click();
                }else {
                driver.findElement(By.xpath(".//ul[@id='docs-menu-main.menu.study.label']/li[3]/menu-link/a")).click();
                }
                
                System.exit(0);

   }


}
