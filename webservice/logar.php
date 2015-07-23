<?php

if(isset($_POST['login']) && isset($_POST['senha'])){
    $login = $_POST['login'];
    $senha = $_POST['senha'];
    
    require './bd/Conexao.php';
    $con = getInstance();
    
    $sql = 'SELECT login FROM usuario WHERE login = :login AND senha = md5(:senha)';
    
    $stmt = $con->prepare($sql);
    
    $stmt->bindParam(':login', $login);
    $stmt->bindParam(':senha', $senha);
    $stmt->execute();
    
    if($stmt->rowCount() == 1){
        $retorno = array('status' => TRUE);
    }else{
        $retorno = array('status' => FALSE);
    }
}else{
    $retorno = array('status' => FALSE);
}
unset($con);
echo json_encode($retorno, JSON_UNESCAPED_UNICODE);