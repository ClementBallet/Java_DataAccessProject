package fr.houseofcode.dap;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author adminHOC
 *
 */
@RestController
public class Index {

    /**
     * Say hello.
     * @return "hello"
     * @param name username.
     * @param more bidon.
     */
    @RequestMapping("/welcome/{data}")
    public String welcome(@RequestParam("nom") final String name, @PathVariable("data") final String more) {
        return "salut " + name + " " + more;
    }
}
