package com.toda.controller;

import com.toda.Exception.NotValidTokenException;
import com.toda.Exception.UnauthorizedException;
import com.toda.dao.ItemDetailsRepository;
import com.toda.model.Item;
import com.toda.model.Item_Details;
import com.toda.service.ItemService;
import com.toda.service.JwtService;
import com.toda.service.TokenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/items")
@Tag(name = "Item API", description = "API for managing items")
 public class ItemController {
    //@RequestMapping("/api/v1/item")
    @Autowired
    private ItemService itemService;

   private ItemDetailsRepository itemDetailsRepository;


    @Autowired
    private TokenService tokenService;

    @Autowired
   private JwtService jwtService;


    @GetMapping("/testToken")
    @Operation(
            summary = "test token for specific item",
            description = "This operation  to check validaty of token of user "
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful request"),
            @ApiResponse(responseCode = "404", description = "Error for client"),
            @ApiResponse(responseCode = "500", description = "Error for server")
    })
     public String getProtectedResource(@RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7); // Remove "Bearer " prefix
        if (tokenService.isTokenValid(jwtToken)) {
            return "Access granted to protected resource.";
        } else {
            return "Invalid token.";
        }
    }

    @GetMapping("/test")
    public String testmethod(){
        return  "hi";
    }

    @PostMapping("/item")
    @Operation(
            summary = "add an item",
            description = "This operation adding an item and its details by using the item ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful request"),
            @ApiResponse(responseCode = "404", description = "Error for client"),
            @ApiResponse(responseCode = "500", description = "Error for server")
    })
      public ResponseEntity<Item> addItem(@RequestBody Item_Details itemDetails, @RequestHeader("Authorization") String token) {
        try {
            String jwtToken = token.substring(7); // Remove "Bearer " prefix
            if (tokenService.isTokenValid(jwtToken)) {
                String userId = jwtService.getExtractUserEmail(jwtToken);
                Item item = new Item();
                // Extract title from description
                String description = itemDetails.getDescription();
                int spaceIndex = description.indexOf(" ");
                String title = (spaceIndex != -1) ? description.substring(0, spaceIndex) : description;

                item.setTitle(title );
                item.setUserId(userId);
                item.setItemDetails(itemDetails);
                Item savedItem = itemService.addItem(item);
                return ResponseEntity.status(HttpStatus.CREATED).body(savedItem);
            } else {
                throw new UnauthorizedException("Invalid token");
            }
        } catch (UnauthorizedException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



    @DeleteMapping("/item/{id}")
    @Operation(
            summary = "delete an item",
            description = "This operation delete an item and its details by using the item ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful request"),
            @ApiResponse(responseCode = "404", description = "Error for client"),
            @ApiResponse(responseCode = "500", description = "Error for server")
    })
     public ResponseEntity<String> deleteItem(@PathVariable("id") int itemId, @RequestHeader("Authorization") String token) {
        try {
            Item item= itemService.findItemById(itemId);
            String jwtToken = token.substring(7); // Remove "Bearer " prefix
            String userId = jwtService.getExtractUserEmail(jwtToken);
            // get user_id from the item and check if equal token id which is subject delete this item

            if (tokenService.isTokenValid(jwtToken) && item.getUserId().equals(userId) ) {



                    itemService.deleteItem(itemId);
                    return ResponseEntity.ok("Item deleted successfully");
                }

              else {
                throw new UnauthorizedException("Invalid token");
            }
        } catch (UnauthorizedException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the item");
        }
    }

    @GetMapping("/item/{id}")
    @Operation(
            summary = "search an item",
            description = "This operation search an item and its details by using the item ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful request"),
            @ApiResponse(responseCode = "404", description = "Error for client"),
            @ApiResponse(responseCode = "500", description = "Error for server")
    })
         public ResponseEntity<Item> searchItem(@PathVariable int id, @RequestHeader("Authorization") String token) {
        try {
            Item item= itemService.findItemById(id);
            String jwtToken = token.substring(7); // Remove "Bearer " prefix
            String userId = jwtService.getExtractUserEmail(jwtToken);
            // get user_id from the item and check if equal token id which is subject delete this item
             if (tokenService.isTokenValid(jwtToken) && item.getUserId().equals(userId)) {
                Item item_res = itemService.searchItem(id);
                return ResponseEntity.ok(item_res);
            } else {
                throw new UnauthorizedException("Invalid token");
            }
        } catch (UnauthorizedException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
          catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @PutMapping("/item/{id}")
    @Operation(
            summary = "Update an item",
            description = "This operation updates an item and its details by using the item ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful request"),
            @ApiResponse(responseCode = "404", description = "Error for client"),
            @ApiResponse(responseCode = "500", description = "Error for server")
    })
    public ResponseEntity<Item> updateItem(
            @PathVariable int id,

            @RequestBody Item_Details itemDetails,
            @RequestHeader("Authorization") String token) {
        try {
            // Validate and parse the token
            String jwtToken = token.substring(7); // Remove "Bearer " prefix
            String userId = jwtService.getExtractUserEmail(jwtToken);

            // Find the existing item
            Item existingItem = itemService.findItemById(id);

            // Check if the token is valid and the user ID matches
            if (tokenService.isTokenValid(jwtToken) && existingItem.getUserId().equals(userId)) {
                // Update the item and its details
                Item updatedItem = itemService.updateItem(id, itemDetails);
                return ResponseEntity.ok(updatedItem);
            } else {
                throw new UnauthorizedException("Invalid token");
            }
        } catch (UnauthorizedException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }





}
