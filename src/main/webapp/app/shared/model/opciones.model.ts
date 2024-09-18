import { IPersonalizaciones } from 'app/shared/model/personalizaciones.model';
import { IVenta } from 'app/shared/model/venta.model';

export interface IOpciones {
  id?: number;
  codigo?: string;
  nombre?: string;
  descripcion?: string;
  precioAdicional?: number;
  personalizacion?: IPersonalizaciones;
  personalizacionOpciones?: IPersonalizaciones | null;
  ventaOpciones?: IVenta[] | null;
}

export const defaultValue: Readonly<IOpciones> = {};
