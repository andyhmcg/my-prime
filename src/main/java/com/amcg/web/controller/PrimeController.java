package com.amcg.web.controller;

import com.amcg.json.JsonResponse;
import com.amcg.web.request.PrimeNumberRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
public class PrimeController {

    @Autowired
    private com.amcg.service.PrimeService primeService;

    @RequestMapping(method = RequestMethod.GET, value = "/prime")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    // Set Add Validation , default values end range , limit etc
    public JsonResponse<List<Integer>> getPrimeNumbers(@Valid @ModelAttribute PrimeNumberRequest primeNumberRequest) {


        //PrimeNumberRequest primeNumberRequest = new PrimeNumberRequest(start, end, limit );
        return new JsonResponse<>(primeService.getPrimes(primeNumberRequest));

    }

    @ModelAttribute
    public PrimeNumberRequest PrimeNumberRequest(@RequestParam(name = "start", required=false, defaultValue = "0") int start,
                                                 @RequestParam(name = "end", required=false, defaultValue = "100") int end,
                                                 @RequestParam(name = "limit", required=false, defaultValue = "25") int limit){

        return new PrimeNumberRequest(start,end,limit);
    }



}