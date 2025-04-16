package com.dharbor.sales.services;

import com.dharbor.sales.api.response.SaleResponse;
import com.dharbor.sales.clients.NotificationsFeignClient;
import com.dharbor.sales.clients.RickMortyApiFeignClient;
import com.dharbor.sales.clients.StockFeignClient;
import com.dharbor.sales.clients.UsersFeignClient;
import com.dharbor.sales.exceptions.SaleNotCompletedException;
import com.dharbor.sales.model.User;
import com.dharbor.sales.model.dto.NewSaleDto;
import com.dharbor.sales.model.rest.Character;
import com.dharbor.sales.model.rest.ProductReservationRequest;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewSalesService {

    private final UsersFeignClient usersFeignClient;

    private final StockFeignClient stockFeignClient;

    private final NotificationsFeignClient notificationsFeignClient;

    private final RickMortyApiFeignClient rickMortyApiFeignClient;

    public SaleResponse newSale(NewSaleDto newSaleDto) {
        User user = findUser(newSaleDto.getUserId());
        String reservationId = reserveProduct(newSaleDto);
        String notification = sendNotification(newSaleDto.getUserId());

        logCharacterInfo();

        return new SaleResponse(user.getName(), reservationId, notification);
    }

    private User findUser(UUID userId) {
        try {
            return usersFeignClient.findById(userId);
        } catch (FeignException.NotFound e) {
            throw new SaleNotCompletedException("User not found", e);
        } catch (FeignException e) {
            throw new SaleNotCompletedException("Failed to fetch user", e);
        }
    }

    private String reserveProduct(NewSaleDto dto) {
        ProductReservationRequest request = new ProductReservationRequest();
        request.setProductId(dto.getProductId());
        request.setQuantity(dto.getQuantity());
        return stockFeignClient.reserve(request);
    }

    private String sendNotification(UUID userId) {
        return notificationsFeignClient.sendNotification(userId.toString());
    }

    private void logCharacterInfo() {
        Character character = rickMortyApiFeignClient.findById(2);
        log.info("Character: {} - {} - {}", character.getName(), character.getSpecies(), character.getStatus());
    }
}
