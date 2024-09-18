import { IVenta } from 'app/shared/model/venta.model';

export interface IAdicionales {
  id?: number;
  nombre?: string;
  descripcion?: string;
  precio?: number;
  precioGratis?: number;
  ventaAdicionales?: IVenta[] | null;
}

export const defaultValue: Readonly<IAdicionales> = {};
