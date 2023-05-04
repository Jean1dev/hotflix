import Keycloak from "keycloak-js";

const keycloakConfig = {
  url: "https://lemur-5.cloud-iam.com/auth",
  realm: "caixinha-auth-server",
  clientId: "react-auth",
};

export const keycloak = new Keycloak(keycloakConfig);
