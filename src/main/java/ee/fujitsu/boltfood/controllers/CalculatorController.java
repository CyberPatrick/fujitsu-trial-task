package ee.fujitsu.boltfood.controllers;

import ee.fujitsu.boltfood.dto.requests.calculatorController.DeliveryFeeRequest;
import ee.fujitsu.boltfood.dto.responses.calculatorController.DeliveryFeeResponse;
import ee.fujitsu.boltfood.services.CalculateDeliveryFeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@Validated
@RestController
@RequestMapping("/api")
public class CalculatorController {
    private final CalculateDeliveryFeeService calculateDeliveryFeeService;

    /**
     * Calculates the delivery fee based on the provided request details. The calculation
     * considers factors such as the city, transport type, and an optional timestamp.
     * The result is rounded to two decimal places.
     *
     * @param deliveryFeeRequest The request containing the necessary details for delivery
     *                           fee calculation, including the city name, the type of transport,
     *                           and an optional timestamp for the calculation.
     * @return A ResponseEntity containing a DeliveryFeeResponse object that includes
     *         the computed delivery fee.
     */
    @GetMapping("calculateDeliveryFee")
    public ResponseEntity<DeliveryFeeResponse> calculateDeliveryFee(@ModelAttribute DeliveryFeeRequest deliveryFeeRequest) {
        var result = calculateDeliveryFeeService.calculateDeliveryFee(deliveryFeeRequest);
        return new ResponseEntity<>(new DeliveryFeeResponse(Math.round(result * 100f) / 100f),
                HttpStatusCode.valueOf(200));
    }
}
