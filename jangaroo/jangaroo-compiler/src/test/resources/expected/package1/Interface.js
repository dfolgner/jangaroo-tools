define("as3/package1/Interface",["module","exports","as3-rt/AS3","as3/package1/SuperInterface"], function($module,$exports,AS3,SuperInterface) { AS3.interface_($module,$exports,/*package package1 {

/**
 * Some ASDoc.
 * /
// this comment should vanish in the API!
public interface Interface extends SuperInterface {
  function doSomething():String;

  function get property():String;
}
}

============================================== Jangaroo part ==============================================*/
{extends_: [SuperInterface]}
);});