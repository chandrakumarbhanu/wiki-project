import com.wiki.builder.WikiDataBuilder;
import com.wiki.facade.AnswerMatcherFacade;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;



class WikiTest {



    @Test
    void testCorrectOutputAnswers() {
        List<String> input00 = new ArrayList<>();

        input00.add("Grevy's zebra and the mountain zebra");
        input00.add("aims to breed zebras that are phenotypically similar to the quagga");
        input00.add("horses and donkeys");
        input00.add("the plains zebra, the Grevy's zebra and the mountain zebra");
        input00.add("subgenus Hippotigris");

        WikiDataBuilder modelBuilder = new WikiDataBuilder();
        AnswerMatcherFacade answerMatcherFacade = new AnswerMatcherFacade();

        answerMatcherFacade.publishAnswers(modelBuilder.build());

        //reading the output file.
        try (Stream<Path> paths = Files.walk(Paths.get(System.getProperty("user.dir")+"/"+"output"))) {

            paths.filter(Files::isRegularFile).forEach(file -> {

                List<String> lines = Collections.emptyList();
                try {
                    lines =
                            Files.readAllLines(file, StandardCharsets.UTF_8);
                    Assertions.assertArrayEquals(input00.toArray(), lines.toArray());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        catch(Exception e){

        }
        finally{

            }


    }

    @Test
    void testInCorrectOutputAnswers() {
        List<String> input00 = new ArrayList<>();

        input00.add("evy's zebra and the mountain zebra");
        input00.add("ms to breed zebras that are phenotypically similar to the quagga");
        input00.add("horses and donkeys");
        input00.add("the plains zebra, the Grevy's zebra and the mountain zebra");
        input00.add("subgenus Hippotigris");

        //reading the output file

        try (Stream<Path> paths = Files.walk(Paths.get(System.getProperty("user.dir")+"/"+"output"))) {

            paths.filter(Files::isRegularFile).forEach(file -> {

                List<String> lines = Collections.emptyList();
                try {
                    lines =
                            Files.readAllLines(file, StandardCharsets.UTF_8);
                    Assertions.assertNotEquals(input00.get(0), lines.get(0));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        catch(Exception e){

        }
        finally{

        }


    }
}

