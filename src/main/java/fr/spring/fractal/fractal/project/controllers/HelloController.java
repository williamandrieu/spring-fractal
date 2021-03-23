package fr.spring.fractal.fractal.project.controllers;

import fr.spring.fractal.fractal.project.fractal.Julia;
import fr.spring.fractal.fractal.project.fractal.Mandelbrot;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executors;

@RestController
public class HelloController {

    int cores = Runtime.getRuntime().availableProcessors();
    Mandelbrot mandelbrot = new Mandelbrot(1000);
    Julia julia = new Julia();




    @RequestMapping(value = "/images/{type}/{height}/{width}/{x}/{y}/{zoom}", headers = "Accept=image/jpeg, image/jpg, image/png, image/gif, image/webm", method = RequestMethod.GET)
    public @ResponseBody
    byte[] image(@PathVariable("type") String type,
                 @PathVariable("height") Integer height,
                 @PathVariable("width") Integer width,
                 @PathVariable("x") Double x,
                 @PathVariable("y") Double y,
                 @PathVariable("zoom") Double zoom) {
        BufferedImage bufferedImage = switch (type) {
            case "mandelbrot" -> mandelbrot.generateFractal(height, width, x, y, zoom, Executors.newFixedThreadPool(cores), true);
            case "julia" -> julia.generateFractal(height, width, x, y, zoom, Executors.newFixedThreadPool(cores), true);
            default -> mandelbrot.generateFractal(height, width, x, y, zoom, Executors.newFixedThreadPool(cores), false);
        };
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write( bufferedImage  , "png", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}