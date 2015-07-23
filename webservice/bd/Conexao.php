<?php
function getInstance() {
    $host = "localhost";
    $dbName = "webservice";
    $user = "root";
    $pass = "";

    return new PDO('mysql:host='.$host.';dbname='.$dbName, $user, $pass,array(PDO::MYSQL_ATTR_INIT_COMMAND => "SET NAMES utf8"));
}