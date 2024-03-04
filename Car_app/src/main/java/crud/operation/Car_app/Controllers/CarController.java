package crud.operation.Car_app.Controllers;

import crud.operation.Car_app.Entities.Car;
import crud.operation.Car_app.Repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/cars")
public class CarController {
    @Autowired
    private CarRepository carRepository;
    @PostMapping("/create")
    public Car postCar(@RequestBody Car car) {
        car.setId(null);
        return carRepository.saveAndFlush(car);
    }
    @GetMapping("/findall")
    public List<Car> findAll() {
        return carRepository.findAll();
    }
    @GetMapping("/getbyid/{id}")
    public Optional<Car> getById(
            @PathVariable Long id
    ) {
        if(carRepository.existsById(id)) {
            return carRepository.findById(id);
        } else {
            return Optional.of(new Car());
        }
    }
    @PatchMapping("/update/type/{id}")
    public Optional<Car> updateType(
            @PathVariable Long id,
            @RequestParam String newType
    ) {
        if(carRepository.existsById(id)) {
            Optional<Car> car = carRepository.findById(id);
            car.get().setType(newType);
            carRepository.saveAndFlush(car.get());
            return car;
        } else {
            return Optional.of(new Car());
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(
            @PathVariable Long id
    ) {
        if(carRepository.existsById(id)) {
            carRepository.deleteById(id);
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }
    @DeleteMapping("/deleteall")
    public void deleteAll() {
        carRepository.deleteAll();
    }
}
