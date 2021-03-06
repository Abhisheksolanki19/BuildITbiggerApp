package com.udacity.gradle.builditbigger.backend;

import com.udacity.abhishek.javajokerlib.TellJokerClass;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import javax.inject.Named;


@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.builditbigger.gradle.udacity.com",
                ownerName = "backend.builditbigger.gradle.udacity.com",
                packagePath = ""
        )
)
public class MyEndpoint {


    @ApiMethod(name = "sayHi")
    public MyBean sayHi(@Named("name") String name) {
        MyBean response = new MyBean();
        response.setData("Hi, " + name);

        return response;
    }


    @ApiMethod(name = "tellJoke")
    public JokeClass tellJoke() {
        JokeClass joke = new JokeClass();
        joke.setText(new TellJokerClass().ShowMeJoke());
        return joke;
    }
}
