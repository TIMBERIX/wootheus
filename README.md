
# wootheus

A prometheus exporter for WooCommerce. Metrics are exposed at `/metrics`.

## Features

- Orders: WooCommerce webhook with action `woocommerce_order_status_xyz` must be pointed to `/woocommerce-webhook`!

## Using with docker

Add the following to your `docker-compose.yml` file:

```yaml
services:
  # ...
  # other services
  # ...
  
  woocommerce-exporter:
    image: timberix/wootheus
    restart: unless-stopped
    ports:
      - 8081:8081
    # optional:
    # environment:
    #  WOOTHEUS_PORT: 8081 # default: 8081
```
