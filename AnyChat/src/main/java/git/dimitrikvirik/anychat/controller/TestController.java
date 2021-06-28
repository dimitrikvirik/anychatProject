package git.dimitrikvirik.anychat.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Api("Test")
public class TestController {

    @ApiOperation(value = "Calculation+", notes = "Addition")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "a", paramType = "path", value = "Number a", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "b", paramType = "path", value = "number b", required = true, dataType = "Long")
    })
    @GetMapping("/{a}/{b}")
    public Integer get(@PathVariable Integer a, @PathVariable Integer b) {
        return a + b;
    }
}