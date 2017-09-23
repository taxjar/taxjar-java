# TaxJar Sales Tax API for Java

Official Java client for [SmartCalcs](http://www.taxjar.com/api/) by [TaxJar](http://www.taxjar.com). For the API documentation, please visit [http://developers.taxjar.com/api](http://developers.taxjar.com/api).

## Getting Started

We recommend installing taxjar-java with [Maven](https://maven.apache.org/what-is-maven.html) or [Gradle](https://gradle.org/). Before authenticating, [get your API key from TaxJar](https://app.taxjar.com/api_sign_up/plus/).

### Maven

Add the following dependency to your project's `pom.xml` file:

```xml
<dependency>
    <groupId>com.taxjar</groupId>
    <artifactId>taxjar-java</artifactId>
    <version>1.0.1</version>
</dependency>
```

### Gradle

Add the following dependency to your project's build file:

```
compile "com.taxjar:taxjar-java:1.0.1"
```

### Manual Installation

You can manually install the following JARs (including dependencies) here:

- [TaxJar JAR](https://github.com/taxjar/taxjar-java/releases/latest)
- [Retrofit](https://search.maven.org/remote_content?g=com.squareup.retrofit2&a=retrofit&v=LATEST)
- [Retrofit Gson Converter](https://search.maven.org/remote_content?g=com.squareup.retrofit2&a=converter-gson&v=LATEST)

## Package Dependencies

taxjar-java is built for **Java 1.7+** and requires the following dependencies:

- [Retrofit](https://github.com/square/retrofit) - Includes [OkHttp](https://github.com/square/okhttp) and [Gson](https://github.com/google/gson)
- [Retrofit Converter Gson](https://github.com/square/retrofit/tree/master/retrofit-converters/gson)

## Authentication

To authenticate with our API, simply instantiate the client with your TaxJar API token:

```java
import com.taxjar.Taxjar;
import com.taxjar.exception.TaxjarException;

public class AuthenticationExample {

    public static void main(String[] args) {
        Taxjar client = new Taxjar("YOUR API TOKEN");
    }

}
```

## Usage

All methods in the `Taxjar` class support synchronous and asynchronous requests. For async examples, see the project's [functional tests](https://github.com/taxjar/taxjar-java/blob/master/src/test/java/com/taxjar/functional/).

### List all tax categories

```java
import com.taxjar.Taxjar;
import com.taxjar.exception.TaxjarException;
import com.taxjar.model.categories.CategoryResponse;

public class CategoryExample {

    public static void main(String[] args) {
        Taxjar client = new Taxjar("YOUR API TOKEN");
        
        try {
            CategoryResponse res = client.categories();
        } catch (TaxjarException e) {
            e.printStackTrace();
        }
    }

}
```

### List tax rates for a location (by zip/postal code)

```java
import com.taxjar.Taxjar;
import com.taxjar.exception.TaxjarException;
import com.taxjar.model.rates.RateResponse;
import java.util.HashMap;
import java.util.Map;

public class RatesExample {

    public static void main(String[] args) {
        Taxjar client = new Taxjar("YOUR API TOKEN");
        
        try {
            Map<String, String> params = new HashMap<>();
            params.put("country", "US");
            params.put("city", "Watts");
            params.put("street", "123 Test St");
            RateResponse res = client.ratesForLocation("90002", params);
        } catch (TaxjarException e) {
            e.printStackTrace();
        }
    }

}
```

### Calculate sales tax for an order

```java
import com.taxjar.Taxjar;
import com.taxjar.exception.TaxjarException;
import com.taxjar.model.taxes.TaxResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaxExample {

    public static void main(String[] args) {
        Taxjar client = new Taxjar("YOUR API TOKEN");
        
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("from_country", "US");
            params.put("from_zip", "92093");
            params.put("from_state", "CA");
            params.put("from_city", "La Jolla");
            params.put("from_street", "9500 Gilman Drive");
            params.put("to_country", "US");
            params.put("to_zip", "90002");
            params.put("to_state", "CA");
            params.put("to_city", "Los Angeles");
            params.put("to_street", "1335 E 103rd St");
            params.put("amount", 15);
            params.put("shipping", 1.5);

            List<Map> nexusAddresses = new ArrayList();
            Map<String, Object> nexusAddress = new HashMap<>();
            nexusAddress.put("country", "US");
            nexusAddress.put("zip", "92093");
            nexusAddress.put("state", "CA");
            nexusAddress.put("city", "La Jolla");
            nexusAddress.put("street", "9500 Gilman Drive");
            nexusAddresses.add(nexusAddress);

            List<Map> lineItems = new ArrayList();
            Map<String, Object> lineItem = new HashMap<>();
            lineItem.put("id", 1);
            lineItem.put("quantity", 1);
            lineItem.put("product_tax_code", "20010");
            lineItem.put("unit_price", 15);
            lineItem.put("discount", 0);
            lineItems.add(lineItem);

            params.put("nexus_addresses", nexusAddresses);
            params.put("line_items", lineItems);

            TaxResponse res = client.taxForOrder(params);
        } catch (TaxjarException e) {
            e.printStackTrace();
        }
    }

}
```

### List order transactions

```java
import com.taxjar.Taxjar;
import com.taxjar.exception.TaxjarException;
import com.taxjar.model.transactions.OrdersResponse;
import java.util.HashMap;
import java.util.Map;

public class ListOrdersExample {

    public static void main(String[] args) {
        Taxjar client = new Taxjar("YOUR API TOKEN");
        
        try {
            Map<String, String> params = new HashMap<>();
            params.put("from_transaction_date", "2015/05/01");
            params.put("to_transaction_date", "2015/05/31");
            OrdersResponse res = client.listOrders(params);
        } catch (TaxjarException e) {
            e.printStackTrace();
        }
    }

}
```

### Show order transaction

```java
import com.taxjar.Taxjar;
import com.taxjar.exception.TaxjarException;
import com.taxjar.model.transactions.OrderResponse;

public class ShowOrderExample {

    public static void main(String[] args) {
        Taxjar client = new Taxjar("YOUR API TOKEN");
        
        try {
            OrderResponse res = client.showOrder("123");
        } catch (TaxjarException e) {
            e.printStackTrace();
        }
    }

}
```

### Create order transaction

```java
import com.taxjar.Taxjar;
import com.taxjar.exception.TaxjarException;
import com.taxjar.model.transactions.OrderResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateOrderExample {

    public static void main(String[] args) {
        Taxjar client = new Taxjar("YOUR API TOKEN");
        
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("transaction_id", "123");
            params.put("transaction_date", "2015/05/04");
            params.put("to_country", "US");
            params.put("to_zip", "90002");
            params.put("to_city", "Los Angeles");
            params.put("to_street", "123 Palm Grove Ln");
            params.put("amount", 16.5);
            params.put("shipping", 1.5);
            params.put("sales_tax", 0.95);

            List<Map> lineItems = new ArrayList();
            Map<String, Object> lineItem = new HashMap<>();
            lineItem.put("quantity", 1);
            lineItem.put("product_identifier", "12-34243-0");
            lineItem.put("description", "Heavy Widget");
            lineItem.put("unit_price", 15);
            lineItem.put("sales_tax", 0.95);
            lineItems.add(lineItem);

            params.put("line_items", lineItems);

            OrderResponse res = client.createOrder(params);
        } catch (TaxjarException e) {
            e.printStackTrace();
        }
    }

}
```

### Update order transaction

```java
import com.taxjar.Taxjar;
import com.taxjar.exception.TaxjarException;
import com.taxjar.model.transactions.OrderResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateOrderExample {

    public static void main(String[] args) {
        Taxjar client = new Taxjar("YOUR API TOKEN");
        
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("transaction_id", "123");
            params.put("transaction_date", "2015/05/04");
            params.put("to_country", "US");
            params.put("to_zip", "90002");
            params.put("to_city", "Los Angeles");
            params.put("to_street", "123 Palm Grove Ln");
            params.put("amount", 17);
            params.put("shipping", 2);
            params.put("sales_tax", 0.95);

            List<Map> lineItems = new ArrayList();
            Map<String, Object> lineItem = new HashMap<>();
            lineItem.put("quantity", 1);
            lineItem.put("product_identifier", "12-34243-0");
            lineItem.put("description", "Heavy Widget");
            lineItem.put("unit_price", 15);
            lineItem.put("sales_tax", 0.95);
            lineItems.add(lineItem);

            params.put("line_items", lineItems);

            OrderResponse res = client.updateOrder("123", params);
        } catch (TaxjarException e) {
            e.printStackTrace();
        }
    }

}
```

### Delete order transaction

```java
import com.taxjar.Taxjar;
import com.taxjar.exception.TaxjarException;
import com.taxjar.model.transactions.OrderResponse;

public class DeleteOrderExample {

    public static void main(String[] args) {
        Taxjar client = new Taxjar("YOUR API TOKEN");
        
        try {
            OrderResponse res = client.deleteOrder("123");
        } catch (TaxjarException e) {
            e.printStackTrace();
        }
    }

}
```

### List refund transactions

```java
import com.taxjar.Taxjar;
import com.taxjar.exception.TaxjarException;
import com.taxjar.model.transactions.RefundsResponse;
import java.util.HashMap;
import java.util.Map;

public class ListRefundsExample {

    public static void main(String[] args) {
        Taxjar client = new Taxjar("YOUR API TOKEN");
        
        try {
            Map<String, String> params = new HashMap<>();
            params.put("from_transaction_date", "2015/05/01");
            params.put("to_transaction_date", "2015/05/31");
            RefundsResponse res = client.listRefunds(params);
        } catch (TaxjarException e) {
            e.printStackTrace();
        }
    }

}
```

### Show refund transaction

```java
import com.taxjar.Taxjar;
import com.taxjar.exception.TaxjarException;
import com.taxjar.model.transactions.RefundResponse;

public class ShowRefundExample {

    public static void main(String[] args) {
        Taxjar client = new Taxjar("YOUR API TOKEN");
        
        try {
            RefundResponse res = client.showRefund("321");
        } catch (TaxjarException e) {
            e.printStackTrace();
        }
    }

}
```

### Create refund transaction

```java
import com.taxjar.Taxjar;
import com.taxjar.exception.TaxjarException;
import com.taxjar.model.transactions.RefundResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateRefundExample {

    public static void main(String[] args) {
        Taxjar client = new Taxjar("YOUR API TOKEN");
        
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("transaction_id", "321");
            params.put("transaction_date", "2015/05/04");
            params.put("to_country", "US");
            params.put("to_zip", "90002");
            params.put("to_city", "Los Angeles");
            params.put("to_street", "123 Palm Grove Ln");
            params.put("amount", 16.5);
            params.put("shipping", 1.5);
            params.put("sales_tax", 0.95);

            List<Map> lineItems = new ArrayList();
            Map<String, Object> lineItem = new HashMap<>();
            lineItem.put("quantity", 1);
            lineItem.put("product_identifier", "12-34243-0");
            lineItem.put("description", "Heavy Widget");
            lineItem.put("unit_price", 15);
            lineItem.put("sales_tax", 0.95);
            lineItems.add(lineItem);

            params.put("line_items", lineItems);

            RefundResponse res = client.createRefund(params);
        } catch (TaxjarException e) {
            e.printStackTrace();
        }
    }

}
```

### Update refund transaction

```java
import com.taxjar.Taxjar;
import com.taxjar.exception.TaxjarException;
import com.taxjar.model.transactions.RefundResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateRefundExample {

    public static void main(String[] args) {
        Taxjar client = new Taxjar("YOUR API TOKEN");
        
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("transaction_id", "321");
            params.put("transaction_date", "2015/05/04");
            params.put("to_country", "US");
            params.put("to_zip", "90002");
            params.put("to_city", "Los Angeles");
            params.put("to_street", "123 Palm Grove Ln");
            params.put("amount", 17);
            params.put("shipping", 2);
            params.put("sales_tax", 0.95);

            List<Map> lineItems = new ArrayList();
            Map<String, Object> lineItem = new HashMap<>();
            lineItem.put("quantity", 1);
            lineItem.put("product_identifier", "12-34243-0");
            lineItem.put("description", "Heavy Widget");
            lineItem.put("unit_price", 15);
            lineItem.put("sales_tax", 0.95);
            lineItems.add(lineItem);

            params.put("line_items", lineItems);

            RefundResponse res = client.updateRefund("321", params);
        } catch (TaxjarException e) {
            e.printStackTrace();
        }
    }

}
```

### Delete refund transaction

```java
import com.taxjar.Taxjar;
import com.taxjar.exception.TaxjarException;
import com.taxjar.model.transactions.RefundResponse;

public class DeleteOrderExample {

    public static void main(String[] args) {
        Taxjar client = new Taxjar("YOUR API TOKEN");
        
        try {
            OrderResponse res = client.deleteRefund("321");
        } catch (TaxjarException e) {
            e.printStackTrace();
        }
    }

}
```

### List nexus regions

```java
import com.taxjar.Taxjar;
import com.taxjar.exception.TaxjarException;
import com.taxjar.model.nexus.RegionResponse;

public class NexusRegionsExample {

    public static void main(String[] args) {
        Taxjar client = new Taxjar("YOUR API TOKEN");
        
        try {
            RegionResponse res = client.nexusRegions();
        } catch (TaxjarException e) {
            e.printStackTrace();
        }
    }

}
```

### Validate a VAT number

```java
import com.taxjar.Taxjar;
import com.taxjar.exception.TaxjarException;
import com.taxjar.model.validations.ValidationResponse;
import java.util.HashMap;
import java.util.Map;

public class ValidateExample {

    public static void main(String[] args) {
        Taxjar client = new Taxjar("YOUR API TOKEN");
        
        try {
            Map<String, String> params = new HashMap<>();
            params.put("vat", "FR40303265045");

            ValidationResponse res = client.validate(params);
        } catch (TaxjarException e) {
            e.printStackTrace();
        }
    }

}
```

### Summarize tax rates for all regions

```java
import com.taxjar.Taxjar;
import com.taxjar.exception.TaxjarException;
import com.taxjar.model.summarized_rates.SummaryRateResponse;

public class SummarizedRatesExample {

    public static void main(String[] args) {
        Taxjar client = new Taxjar("YOUR API TOKEN");
        
        try {
            SummaryRateResponse res = client.summaryRates();
        } catch (TaxjarException e) {
            e.printStackTrace();
        }
    }

}
```

## Tests

We use [JUnit](http://junit.org/) v3.8.1 with a custom interceptor for Retrofit to directly test client methods.

## More Information

More information can be found at [TaxJar Developers](http://developers.taxjar.com).

## License

taxjar-java is released under the [MIT License](https://github.com/taxjar/taxjar-java/blob/master/LICENSE.txt).

## Support

Bug reports and feature requests should be filed on the [GitHub issue tracking page](https://github.com/taxjar/taxjar-java/issues). 

## Contributing

1. Fork it
2. Create your feature branch (`git checkout -b my-new-feature`)
3. Commit your changes (`git commit -am 'Add some feature'`)
4. Push to the branch (`git push origin my-new-feature`)
5. Create new pull request