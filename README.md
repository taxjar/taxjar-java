# TaxJar Sales Tax API for Java ![GitHub tag (latest SemVer)](https://img.shields.io/github/v/tag/taxjar/taxjar-java?style=flat-square&label=release&sort=semver)

<a href="https://developers.taxjar.com"><img src="https://www.taxjar.com/img/TJ_logo_color_office_png.png" alt="TaxJar" width="220"></a>

Official Java client for the [TaxJar API](https://www.taxjar.com/api/). For the API documentation, please visit [https://developers.taxjar.com/api](https://developers.taxjar.com/api/reference/?java).

<hr>

[Getting Started](#getting-started)<br>
[Package Dependencies](#package-dependencies)<br>
[Authentication](#authentication)<br>
[Usage](#usage)<br>
[Custom Options](#custom-options)<br>
[Sandbox Environment](#sandbox-environment)<br>
[Tests](#tests)<br>
[More Information](#more-information)<br>
[License](#license)<br>
[Support](#support)<br>
[Contributing](#contributing)

<hr>

## Getting Started

We recommend installing taxjar-java with [Maven](https://maven.apache.org/what-is-maven.html) or [Gradle](https://gradle.org/). Before authenticating, [get your API key from TaxJar](https://app.taxjar.com/api_sign_up/plus/).

### Maven

Add the following dependency to your project's `pom.xml` file:

```xml
<dependency>
    <groupId>com.taxjar</groupId>
    <artifactId>taxjar-java</artifactId>
    <version>4.0.0</version>
</dependency>
```

### Gradle

Add the following dependency to your project's build file:

```
compile "com.taxjar:taxjar-java:4.0.0"
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

You're now ready to use TaxJar! [Check out our quickstart guide](https://developers.taxjar.com/api/guides/java/#java-quickstart) to get up and running quickly.

## Usage

[`categories` - List all tax categories](#list-all-tax-categories-api-docs)<br>
[`taxForOrder` - Calculate sales tax for an order](#calculate-sales-tax-for-an-order-api-docs)<br>
[`listOrders` - List order transactions](#list-order-transactions-api-docs)<br>
[`showOrder` - Show order transaction](#show-order-transaction-api-docs)<br>
[`createOrder` - Create order transaction](#create-order-transaction-api-docs)<br>
[`updateOrder` - Update order transaction](#update-order-transaction-api-docs)<br>
[`deleteOrder` - Delete order transaction](#delete-order-transaction-api-docs)<br>
[`listRefunds` - List refund transactions](#list-refund-transactions-api-docs)<br>
[`showRefund` - Show refund transaction](#show-refund-transaction-api-docs)<br>
[`createRefund` - Create refund transaction](#create-refund-transaction-api-docs)<br>
[`updateRefund` - Update refund transaction](#update-refund-transaction-api-docs)<br>
[`deleteRefund` - Delete refund transaction](#delete-refund-transaction-api-docs)<br>
[`listCustomers` - List customers](#list-customers-api-docs)<br>
[`showCustomer` - Show customer](#show-customer-api-docs)<br>
[`createCustomer` - Create customer](#create-customer-api-docs)<br>
[`updateCustomer` - Update customer](#update-customer-api-docs)<br>
[`deleteCustomer` - Delete customer](#delete-customer-api-docs)<br>
[`ratesForLocation` - List tax rates for a location (by zip/postal code)](#list-tax-rates-for-a-location-by-zippostal-code-api-docs)<br>
[`nexusRegions` - List nexus regions](#list-nexus-regions-api-docs)<br>
[`validateAddress` - Validate an address](#validate-an-address-api-docs)<br>
[`validateVat` - Validate a VAT number](#validate-a-vat-number-api-docs)<br>
[`summaryRates` - Summarize tax rates for all regions](#summarize-tax-rates-for-all-regions-api-docs)

<hr>

All methods in the `Taxjar` class support synchronous and asynchronous requests. For async examples, see the project's [functional tests](https://github.com/taxjar/taxjar-java/blob/master/src/test/java/com/taxjar/functional/).

### List all tax categories <small>_([API docs](https://developers.taxjar.com/api/reference/?java#get-list-tax-categories))_</small>

> The TaxJar API provides product-level tax rules for a subset of product categories. These categories are to be used for products that are either exempt from sales tax in some jurisdictions or are taxed at reduced rates. You need not pass in a product tax code for sales tax calculations on product that is fully taxable. Simply leave that parameter out.

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

### Calculate sales tax for an order <small>_([API docs](https://developers.taxjar.com/api/reference/?java#post-calculate-sales-tax-for-an-order))_</small>

> Shows the sales tax that should be collected for a given order.

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

### List order transactions <small>_([API docs](https://developers.taxjar.com/api/reference/?java#get-list-order-transactions))_</small>

> Lists existing order transactions created through the API.

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

### Show order transaction <small>_([API docs](https://developers.taxjar.com/api/reference/?java#get-show-an-order-transaction))_</small>

> Shows an existing order transaction created through the API.

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

### Create order transaction <small>_([API docs](https://developers.taxjar.com/api/reference/?java#post-create-an-order-transaction))_</small>

> Creates a new order transaction.

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
            params.put("from_country", "US");
            params.put("from_zip", "92093");
            params.put("from_state", "CA");
            params.put("from_city", "La Jolla");
            params.put("from_street", "9500 Gilman Drive");
            params.put("to_country", "US");
            params.put("to_zip", "90002");
            params.put("to_state", "CA");
            params.put("to_city", "Los Angeles");
            params.put("to_street", "123 Palm Grove Ln");
            params.put("amount", 16.5);
            params.put("shipping", 1.5);
            params.put("sales_tax", 0.95);

            List<Map> lineItems = new ArrayList();
            Map<String, Object> lineItem = new HashMap<>();
            lineItem.put("id", "1");
            lineItem.put("quantity", 1);
            lineItem.put("product_identifier", "12-34243-0");
            lineItem.put("description", "Heavy Widget");
            lineItem.put("unit_price", 15);
            lineItem.put("discount", 0);
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

### Update order transaction <small>_([API docs](https://developers.taxjar.com/api/reference/?java#put-update-an-order-transaction))_</small>

> Updates an existing order transaction created through the API.

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
            params.put("from_country", "US");
            params.put("from_zip", "92093");
            params.put("from_state", "CA");
            params.put("from_city", "La Jolla");
            params.put("from_street", "9500 Gilman Drive");
            params.put("to_country", "US");
            params.put("to_zip", "90002");
            params.put("to_state", "CA");
            params.put("to_city", "Los Angeles");
            params.put("to_street", "123 Palm Grove Ln");
            params.put("amount", 17);
            params.put("shipping", 2);
            params.put("sales_tax", 0.95);

            List<Map> lineItems = new ArrayList();
            Map<String, Object> lineItem = new HashMap<>();
            lineItem.put("id", "1");
            lineItem.put("quantity", 1);
            lineItem.put("product_identifier", "12-34243-0");
            lineItem.put("description", "Heavy Widget");
            lineItem.put("unit_price", 15);
            lineItem.put("discount", 0);
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

### Delete order transaction <small>_([API docs](https://developers.taxjar.com/api/reference/?java#delete-delete-an-order-transaction))_</small>

> Deletes an existing order transaction created through the API.

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

### List refund transactions <small>_([API docs](https://developers.taxjar.com/api/reference/?java#get-list-refund-transactions))_</small>

> Lists existing refund transactions created through the API.

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

### Show refund transaction <small>_([API docs](https://developers.taxjar.com/api/reference/?java#get-show-a-refund-transaction))_</small>

> Shows an existing refund transaction created through the API.

```java
import com.taxjar.Taxjar;
import com.taxjar.exception.TaxjarException;
import com.taxjar.model.transactions.RefundResponse;

public class ShowRefundExample {

    public static void main(String[] args) {
        Taxjar client = new Taxjar("YOUR API TOKEN");

        try {
            RefundResponse res = client.showRefund("123-refund");
        } catch (TaxjarException e) {
            e.printStackTrace();
        }
    }

}
```

### Create refund transaction <small>_([API docs](https://developers.taxjar.com/api/reference/?java#post-create-a-refund-transaction))_</small>

> Creates a new refund transaction.

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
            params.put("transaction_id", "123-refund");
            params.put("transaction_reference_id", "123");
            params.put("transaction_date", "2015/05/04");
            params.put("from_country", "US");
            params.put("from_zip", "92093");
            params.put("from_state", "CA");
            params.put("from_city", "La Jolla");
            params.put("from_street", "9500 Gilman Drive");
            params.put("to_country", "US");
            params.put("to_zip", "90002");
            params.put("to_state", "CA");
            params.put("to_city", "Los Angeles");
            params.put("to_street", "123 Palm Grove Ln");
            params.put("amount", -16.5);
            params.put("shipping", -1.5);
            params.put("sales_tax", -0.95);

            List<Map> lineItems = new ArrayList();
            Map<String, Object> lineItem = new HashMap<>();
            lineItem.put("id", "1");
            lineItem.put("quantity", 1);
            lineItem.put("product_identifier", "12-34243-0");
            lineItem.put("description", "Heavy Widget");
            lineItem.put("unit_price", -15);
            lineItem.put("discount", -0);
            lineItem.put("sales_tax", -0.95);
            lineItems.add(lineItem);

            params.put("line_items", lineItems);

            RefundResponse res = client.createRefund(params);
        } catch (TaxjarException e) {
            e.printStackTrace();
        }
    }

}
```

### Update refund transaction <small>_([API docs](https://developers.taxjar.com/api/reference/?java#put-update-a-refund-transaction))_</small>

> Updates an existing refund transaction created through the API.

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
            params.put("transaction_id", "123-refund");
            params.put("transaction_reference_id", "123");
            params.put("transaction_date", "2015/05/04");
            params.put("from_country", "US");
            params.put("from_zip", "92093");
            params.put("from_state", "CA");
            params.put("from_city", "La Jolla");
            params.put("from_street", "9500 Gilman Drive");
            params.put("to_country", "US");
            params.put("to_zip", "90002");
            params.put("to_state", "CA");
            params.put("to_city", "Los Angeles");
            params.put("to_street", "123 Palm Grove Ln");
            params.put("amount", -17);
            params.put("shipping", -2);
            params.put("sales_tax", -0.95);

            List<Map> lineItems = new ArrayList();
            Map<String, Object> lineItem = new HashMap<>();
            lineItem.put("id", "1");
            lineItem.put("quantity", 1);
            lineItem.put("product_identifier", "12-34243-0");
            lineItem.put("description", "Heavy Widget");
            lineItem.put("unit_price", -15);
            lineItem.put("discount", -0);
            lineItem.put("sales_tax", -0.95);
            lineItems.add(lineItem);

            params.put("line_items", lineItems);

            RefundResponse res = client.updateRefund("321", params);
        } catch (TaxjarException e) {
            e.printStackTrace();
        }
    }

}
```

### Delete refund transaction <small>_([API docs](https://developers.taxjar.com/api/reference/?java#delete-delete-a-refund-transaction))_</small>

> Deletes an existing refund transaction created through the API.

```java
import com.taxjar.Taxjar;
import com.taxjar.exception.TaxjarException;
import com.taxjar.model.transactions.RefundResponse;

public class DeleteOrderExample {

    public static void main(String[] args) {
        Taxjar client = new Taxjar("YOUR API TOKEN");

        try {
            OrderResponse res = client.deleteRefund("123-refund");
        } catch (TaxjarException e) {
            e.printStackTrace();
        }
    }

}
```

### List customers <small>_([API docs](https://developers.taxjar.com/api/reference/?java#get-list-customers))_</small>

> Lists existing customers created through the API.

```java
import com.taxjar.Taxjar;
import com.taxjar.exception.TaxjarException;
import com.taxjar.model.customers.CustomersResponse;
import java.util.HashMap;
import java.util.Map;

public class ListCustomersExample {

    public static void main(String[] args) {
        Taxjar client = new Taxjar("YOUR API TOKEN");

        try {
            CustomersResponse res = client.listCustomers();
        } catch (TaxjarException e) {
            e.printStackTrace();
        }
    }

}
```

### Show customer <small>_([API docs](https://developers.taxjar.com/api/reference/?java#get-show-a-customer))_</small>

> Shows an existing customer created through the API.

```java
import com.taxjar.Taxjar;
import com.taxjar.exception.TaxjarException;
import com.taxjar.model.customers.CustomerResponse;

public class ShowCustomerExample {

    public static void main(String[] args) {
        Taxjar client = new Taxjar("YOUR API TOKEN");

        try {
            CustomerResponse res = client.showCustomer("123");
        } catch (TaxjarException e) {
            e.printStackTrace();
        }
    }

}
```

### Create customer <small>_([API docs](https://developers.taxjar.com/api/reference/?java#post-create-a-customer))_</small>

> Creates a new customer.

```java
import com.taxjar.Taxjar;
import com.taxjar.exception.TaxjarException;
import com.taxjar.model.customers.CustomerResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateCustomerExample {

    public static void main(String[] args) {
        Taxjar client = new Taxjar("YOUR API TOKEN");

        try {
            Map<String, Object> params = new HashMap<>();
            params.put("customer_id", "123");
            params.put("exemption_type", "wholesale");
            params.put("name", "Dunder Mifflin Paper Company");
            params.put("country", "US");
            params.put("state", "PA");
            params.put("zip", "18504");
            params.put("city", "Scranton");
            params.put("street", "1725 Slough Avenue");

            List<Map> exemptRegions = new ArrayList();

            Map<String, String> exemptRegion = new HashMap<>();
            exemptRegion.put("country", "US");
            exemptRegion.put("state", "FL");

            Map<String, String> exemptRegion2 = new HashMap<>();
            exemptRegion.put("country", "US");
            exemptRegion.put("state", "PA");

            exemptRegions.add(exemptRegion);
            exemptRegions.add(exemptRegion2);

            params.put("exempt_regions", exemptRegions);

            CustomerResponse res = client.createCustomer(params);
        } catch (TaxjarException e) {
            e.printStackTrace();
        }
    }

}
```

### Update customer <small>_([API docs](https://developers.taxjar.com/api/reference/?java#put-update-a-customer))_</small>

> Updates an existing customer created through the API.

```java
import com.taxjar.Taxjar;
import com.taxjar.exception.TaxjarException;
import com.taxjar.model.customers.CustomerResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateCustomerExample {

    public static void main(String[] args) {
        Taxjar client = new Taxjar("YOUR API TOKEN");

        try {
            Map<String, Object> params = new HashMap<>();
            params.put("customer_id", "123");
            params.put("exemption_type", "wholesale");
            params.put("name", "Sterling Cooper");
            params.put("country", "US");
            params.put("state", "NY");
            params.put("zip", "10010");
            params.put("city", "New York");
            params.put("street", "405 Madison Ave");

            List<Map> exemptRegions = new ArrayList();

            Map<String, String> exemptRegion = new HashMap<>();
            exemptRegion.put("country", "US");
            exemptRegion.put("state", "NY");

            exemptRegions.add(exemptRegion);

            params.put("exempt_regions", exemptRegions);

            CustomerResponse res = client.updateCustomer("123", params);
        } catch (TaxjarException e) {
            e.printStackTrace();
        }
    }

}
```

### Delete customer <small>_([API docs](https://developers.taxjar.com/api/reference/?java#delete-delete-a-customer))_</small>

> Deletes an existing customer created through the API.

```java
import com.taxjar.Taxjar;
import com.taxjar.exception.TaxjarException;
import com.taxjar.model.customers.CustomerResponse;

public class DeleteCustomerExample {

    public static void main(String[] args) {
        Taxjar client = new Taxjar("YOUR API TOKEN");

        try {
            CustomerResponse res = client.deleteCustomer("123");
        } catch (TaxjarException e) {
            e.printStackTrace();
        }
    }

}
```

### List tax rates for a location (by zip/postal code) <small>_([API docs](https://developers.taxjar.com/api/reference/?java#get-show-tax-rates-for-a-location))_</small>

> Shows the sales tax rates for a given location.
>
> **Please note this method only returns the full combined rate for a given location.** It does not support nexus determination, sourcing based on a ship from and ship to address, shipping taxability, product exemptions, customer exemptions, or sales tax holidays. We recommend using [`taxForOrder` to accurately calculate sales tax for an order](#calculate-sales-tax-for-an-order-api-docs)).

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

### List nexus regions <small>_([API docs](https://developers.taxjar.com/api/reference/?java#get-list-nexus-regions))_</small>

> Lists existing nexus locations for a TaxJar account.

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

### Validate an address <small>_([API docs](https://developers.taxjar.com/api/reference/?java#post-validate-an-address))_</small>

> Validates a customer address and returns back a collection of address matches. **Address validation requires a [TaxJar Plus](https://www.taxjar.com/plus/) subscription.**

```java
import com.taxjar.Taxjar;
import com.taxjar.exception.TaxjarException;
import com.taxjar.model.validations.AddressResponse;
import java.util.HashMap;
import java.util.Map;

public class ValidateAddressExample {

    public static void main(String[] args) {
        Taxjar client = new Taxjar("YOUR API TOKEN");

        try {
            Map<String, Object> params = new HashMap<>();
            params.put("country", "US");
            params.put("state", "AZ");
            params.put("zip", "85297");
            params.put("city", "Gilbert");
            params.put("street", "3301 South Greenfield Rd");

            ValidateAddressResponse res = client.validateAddress(params);
        } catch (TaxjarException e) {
            e.printStackTrace();
        }
    }

}
```

### Validate a VAT number <small>_([API docs](https://developers.taxjar.com/api/reference/?java#get-validate-a-vat-number))_</small>

> Validates an existing VAT identification number against [VIES](http://ec.europa.eu/taxation_customs/vies/).

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

            ValidationResponse res = client.validateVat(params);
        } catch (TaxjarException e) {
            e.printStackTrace();
        }
    }

}
```

### Summarize tax rates for all regions <small>_([API docs](https://developers.taxjar.com/api/reference/?java#get-summarize-tax-rates-for-all-regions))_</small>

> Retrieve minimum and average sales tax rates by region as a backup.
>
> This method is useful for periodically pulling down rates to use if the TaxJar API is unavailable. However, it does not support nexus determination, sourcing based on a ship from and ship to address, shipping taxability, product exemptions, customer exemptions, or sales tax holidays. We recommend using [`taxForOrder` to accurately calculate sales tax for an order](#calculate-sales-tax-for-an-order-api-docs).

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

## Custom Options

You can pass additional options using `setApiConfig` or when instantiating the client for the following:

### API Version / Headers

Pass an API version with `x-api-version` or pass additional request headers:

```java
import com.taxjar.Taxjar;
import java.util.HashMap;
import java.util.Map;

public class CustomHeaderExample {

    public static void main(String[] args) {
        // Custom header when instantiating the client
        Map<String, Object> params = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        
        headers.put("x-api-version", "2020-08-07");
        params.put("headers", headers);

        Taxjar client = new Taxjar("YOUR API TOKEN", params);

        // Custom header via `setApiConfig`
        client.setApiConfig("headers", headers);
    }

}
```

### Timeouts

The default timeout is 30 seconds (specified in milliseconds).

```java
import com.taxjar.Taxjar;
import com.taxjar.exception.TaxjarException;
import java.util.HashMap;
import java.util.Map;

public class CustomTimeoutExample {

    public static void main(String[] args) {
        // Custom timeout when instantiating the client
        Map<String, Object> params = new HashMap<>();
        params.put("timeout", 30 * 1000);

        Taxjar client = new Taxjar("YOUR API TOKEN", params);

        // Custom timeout via `setApiConfig`
        client.setApiConfig("timeout", 30 * 1000);
    }

}
```

## Sandbox Environment

You can easily configure the client to use the [TaxJar Sandbox](https://developers.taxjar.com/api/reference/?java#sandbox-environment):

```java
import com.taxjar.Taxjar;
import com.taxjar.exception.TaxjarException;
import java.util.HashMap;
import java.util.Map;

public class SandboxExample {

    public static void main(String[] args) {
        Map<String, Object> params = new HashMap<>();
        params.put("apiUrl", Taxjar.SANDBOX_API_URL);

        Taxjar client = new Taxjar("YOUR SANDBOX API TOKEN", params);
    }

}
```

## Tests

We use [JUnit](https://junit.org/) v3.8.1 with a custom interceptor for Retrofit to directly test client methods.

## More Information

More information can be found at [TaxJar Developers](https://developers.taxjar.com).

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
